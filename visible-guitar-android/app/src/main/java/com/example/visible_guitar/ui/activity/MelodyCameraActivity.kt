package com.example.visible_guitar.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.domain.ArucoAugmentedReality
import com.example.domain.ar.NeckLabelsDrawer
import com.example.domain.ar.base.AugmentedReality
import com.example.domain.ar.generator.NeckMesh
import com.example.domain.ar.model.LabelNote
import com.example.domain.ar.recognizer.aruco.ArucoRecognizer
import com.example.domain.ar.recognizer.aruco.marker.RectangleMarkerFactory
import com.example.domain.ar.recognizer.aruco.model.FrameEntity
import com.example.visible_guitar.R
import com.example.visible_guitar.common.extensions.click
import com.example.visible_guitar.common.extensions.showBar
import com.example.visible_guitar.databinding.ActivityMelodyCameraBinding
import com.example.visible_guitar.model.Chord
import com.example.visible_guitar.model.Melody
import com.example.visible_guitar.model.states.State
import com.example.visible_guitar.viewmodel.CameraMelodyDetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import org.opencv.android.CameraBridgeViewBase
import org.opencv.core.CvType
import org.opencv.core.Mat

@AndroidEntryPoint
class MelodyCameraActivity : BaseCameraActivity(
    R.layout.activity_melody_camera,
    R.id.melodyCamera
) {

    private val viewModel by viewModels<CameraMelodyDetailViewModel>()
    private val viewBinding by viewBinding<ActivityMelodyCameraBinding>()
    private var id: Int? = null
    private var augmentedReality: AugmentedReality? = null

    private val neckMesh by lazy {
        NeckMesh.Builder()
            .setSizeOfNeck(currentFrame.size())
            .build()
    }

    private val drawer by lazy {
        NeckLabelsDrawer.Builder()

            .setNeckMesh(neckMesh)
//            .build()
    }

    private val recognizer by lazy {
        ArucoRecognizer.Builder()
            .addMarkerFactory(RectangleMarkerFactory())
            .build()
    }

    companion object {
        const val ID = "ID"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        id = intent.getIntExtra(ID, 0)
        viewModel.loadMelody(id!!)
    }

    override fun setupListeners() {
        viewBinding.flipCamera.setOnClickListener {
            swapCamera()
        }

        viewBinding.back.setOnClickListener {
            val i = Intent(this, MainActivity::class.java)
            i.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            finish()
        }
        viewBinding.end.setOnClickListener {
            viewModel.addLearnedMelody(id!!)
            val i = Intent(this, MainActivity::class.java)
            i.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            finish()
        }
    }


    override fun setupCameraThread() {

            viewModel.melodyState.observe(this) { state ->
                runOnUiThread {
                viewBinding.state.isVisible = state is State.Loading
                when (state) {
                    is State.Error -> showBar(viewBinding.melodyCameraActivity, state.error)
                    is State.Loading -> viewBinding.state.visibility = View.VISIBLE
                    is State.Success -> {
                        viewBinding.state.visibility = View.INVISIBLE
                        val melody = state.data
                        var numberOfChord = 1
                        val chordFirst = melody.chords[numberOfChord - 1]
                        viewBinding.prevChord.isVisible = numberOfChord != 1
                        viewBinding.nextChord.isVisible = numberOfChord != melody.chords.size
                        viewBinding.chord.text = getString(
                            R.string.currentChordOnCamera,
                            chordFirst.name,
                            numberOfChord.toString(),
                            melody.chords.size.toString()
                        )
                        drawer.setLabelNotes(chordFirst.notes.map { c -> LabelNote(c.string_number, c.fret_number) })

                        viewBinding.nextChord.click().onEach {
                            drawer.deleteLabelNotes()
                            numberOfChord++
                            if (numberOfChord - 1 > melody.chords.size - 1) {
                                return@onEach
                            }
                            val chord = melody.chords[numberOfChord - 1]
                            val isNotStart = numberOfChord != 1
                            val isNotEnd = numberOfChord != melody.chords.size
                            viewBinding.prevChord.isVisible = isNotStart
                            viewBinding.nextChord.isVisible = isNotEnd
                            viewBinding.end.isVisible = !isNotEnd
                            viewBinding.chord.text = getString(
                                R.string.currentChordOnCamera,
                                chord.name,
                                numberOfChord.toString(),
                                melody.chords.size.toString()
                            )
                            drawer.setLabelNotes(chord.notes.map { note -> LabelNote(note.string_number, note.fret_number) })
                        }.launchIn(lifecycleScope)
//                        lifecycleScope.launch {
                        viewBinding.prevChord.click().onEach {
                            drawer.deleteLabelNotes()
                            numberOfChord--
                            if (numberOfChord <= 0) {
                                return@onEach
                            }
                            val chord = melody.chords[numberOfChord - 1]
                            val isNotStart = numberOfChord != 1
                            val isNotEnd = numberOfChord != melody.chords.size
                            viewBinding.prevChord.isVisible = isNotStart
                            viewBinding.nextChord.isVisible = isNotEnd
                            viewBinding.end.isVisible = !isNotEnd
                            viewBinding.chord.text = getString(
                                R.string.currentChordOnCamera,
                                chord.name,
                                numberOfChord.toString(),
                                melody.chords.size.toString()
                            )
                            drawer.setLabelNotes(chord.notes.map { note -> LabelNote(note.string_number, note.fret_number) })

                        }.launchIn(lifecycleScope)


                    }
                }
            }
        }
    }

    override fun handleFrame(inputFrame: CameraBridgeViewBase.CvCameraViewFrame): Mat {
        currentFrame = inputFrame.rgba()
        val d = drawer
            .setFirstFrame(Mat.zeros(currentFrame.size(), CvType.CV_8UC4))
            .build()
        augmentedReality = ArucoAugmentedReality(d.draw(), recognizer)
        runOnUiThread {
            setFpsLabel(viewBinding.fps)
        }
        return augmentedReality?.augment(FrameEntity(currentFrame, inputFrame.gray())) ?: currentFrame
    }


}