package com.example.visible_guitar.ui.activity


import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.core.view.isVisible
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.domain.ar.aruco.model.FrameEntity
import com.example.visible_guitar.R
import com.example.visible_guitar.common.extensions.showBar
import com.example.visible_guitar.common.util.getRandomColorRGB
import com.example.visible_guitar.databinding.ActivityCameraBinding
import com.example.visible_guitar.model.states.State
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