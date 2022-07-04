package com.example.visible_guitar.ui.activity


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.domain.ArucoAugmentedReality
import com.example.domain.ar.NeckMeshDrawerWrapper
import com.example.domain.ar.NeckLabelsDrawer
import com.example.domain.ar.base.AugmentedReality
import com.example.domain.ar.generator.NeckMesh
import com.example.domain.ar.model.LabelNote
import com.example.domain.ar.recognizer.aruco.ArucoRecognizer
import com.example.domain.ar.recognizer.aruco.marker.RectangleMarkerFactory
import com.example.domain.ar.recognizer.aruco.model.FrameEntity
import com.example.visible_guitar.R

import com.example.visible_guitar.common.extensions.showBar
import com.example.visible_guitar.databinding.ActivityCameraBinding
import com.example.visible_guitar.model.Chord
import com.example.visible_guitar.model.states.State
import com.example.visible_guitar.viewmodel.CameraChordDetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.opencv.android.*
import org.opencv.core.*


@AndroidEntryPoint
class ChordCameraActivity : BaseCameraActivity(
    R.layout.activity_camera,
    R.id.camera
)
{
    private val viewModel by viewModels<CameraChordDetailViewModel>()
    private val viewBinding by viewBinding<ActivityCameraBinding>()
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
            .setFirstFrame(Mat.zeros(currentFrame.size(), CvType.CV_8UC4))
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
        viewModel.loadChord(id!!)
    }

    override fun setupListeners() {

        viewBinding.back.setOnClickListener {
            val i = Intent(this, MainActivity::class.java)
            i.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            finish()
        }
        viewBinding.end.setOnClickListener {
            viewModel.addLearnedChord(id!!)
            val i = Intent(this, MainActivity::class.java)
            i.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            finish()
        }

        viewBinding.flipCamera.setOnClickListener {
            swapCamera()
        }

    }

    override fun setupCameraThread() {
        runOnUiThread {
            viewModel.chordState.observe(this) { state ->
                viewBinding.state.isVisible = state is State.Loading
                when (state) {
                    is State.Error -> showBar(viewBinding.cameraActivity, state.error)
                    is State.Loading -> viewBinding.state.visibility = View.VISIBLE
                    is State.Success -> {
                        viewBinding.state.visibility = View.INVISIBLE
                        val chord = state.data
                        viewBinding.chord.text = getString(R.string.chordOnCamera, chord.name)
                        val d = drawer.setLabelNotes(
                            chord.notes.map { LabelNote(it.string_number, it.fret_number) }
                        ).build()
                        augmentedReality = ArucoAugmentedReality(d.draw(), recognizer)
                    }
                }
            }
        }
    }




    override fun handleFrame(inputFrame: CameraBridgeViewBase.CvCameraViewFrame): Mat {
        currentFrame = inputFrame.rgba()
        runOnUiThread {
            setFpsLabel(viewBinding.fps)
        }
        return augmentedReality?.augment(FrameEntity(currentFrame, inputFrame.gray())) ?: currentFrame
    }
}