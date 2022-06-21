package com.example.visible_guitar.ui.activity


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.fragment.app.commit
import androidx.navigation.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.domain.ar.aruco.model.FrameEntity
import com.example.domain.model.ChordEntity
import com.example.domain.model.InstrumentEntity
import com.example.domain.model.NoteEntity
import com.example.visible_guitar.R
import com.example.visible_guitar.common.extensions.showBar
import com.example.visible_guitar.common.util.getRandomColorRGB
import com.example.visible_guitar.databinding.ActivityCameraBinding
import com.example.visible_guitar.model.Chord
import com.example.visible_guitar.model.states.State
import com.example.visible_guitar.ui.fragment.ChordDetailFragment
import com.example.visible_guitar.viewmodel.CameraChordDetailViewModel
import dagger.hilt.android.AndroidEntryPoint
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

    companion object {
        const val ID = "ID"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val id = intent.getIntExtra(ID, 0)
        viewModel.loadChord(id)
    }

    override fun setupListeners() {

        viewBinding.back.setOnClickListener {
            val i = Intent(this, MainActivity::class.java)
            i.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            finish()
        }
        viewBinding.end.setOnClickListener {
//            val navController = findNavController(R.id.nav_host_fragment_activity_main)
//            navController.navigateUp()
//            navController.navigate(R.id.navigation_profile)
        }

    }

    override fun handleFrame(inputFrame: CameraBridgeViewBase.CvCameraViewFrame): Mat {
        currentFrame = inputFrame.rgba()
        runOnUiThread {
            setFpsLabel(viewBinding.fps)
            viewModel.chordState.observe(this) { state ->
                viewBinding.state.isVisible = state is State.Loading
                when (state) {
                    is State.Error -> showBar(viewBinding.cameraActivity, state.error)
                    is State.Loading -> viewBinding.state.visibility = View.VISIBLE
                    is State.Success -> {
                        viewBinding.state.visibility = View.INVISIBLE
                        val currentChord = state.data
                        viewBinding.chord.text = getString(R.string.chordOnCamera, currentChord.name)
                        arucoAugmentedReality.setMusicalElement(ChordEntity(
                            currentChord.id,
                            InstrumentEntity(currentChord.instrument.id, currentChord.instrument.name),
                            currentChord.name,
                            currentChord.notes.map { NoteEntity(it.id, it.name, it.string_number, it.fret_number) }
                        ))
                    }
                }
            }
        }
        currentFrame = arucoAugmentedReality.augment(
            FrameEntity(
                currentFrame,
                inputFrame.gray()
            )
        )
        return currentFrame

    }
}