package com.example.visible_guitar.ui.fragment

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.visible_guitar.R
import com.example.visible_guitar.databinding.FragmentCameraBinding
import com.example.visible_guitar.viewmodel.CameraViewModel
import dagger.hilt.android.AndroidEntryPoint
import org.opencv.android.BaseLoaderCallback
import org.opencv.android.CameraBridgeViewBase
import org.opencv.android.LoaderCallbackInterface
import org.opencv.android.OpenCVLoader
import org.opencv.core.CvType
import org.opencv.core.Mat

@AndroidEntryPoint
class CameraFragment : Fragment(),
    CameraBridgeViewBase.CvCameraViewListener2 {

    companion object {
        private val TAG = CameraFragment::class.java.simpleName
        private const val CAMERA_PERMISSION_REQUEST = 1
    }

    private val viewModel by viewModels<CameraViewModel>()
    private val viewBinding by viewBinding<FragmentCameraBinding>()
    private lateinit var cameraBridge: CameraBridgeViewBase
    private lateinit var currentFrame: Mat

    private val loader = object : BaseLoaderCallback(activity) {
        override fun onManagerConnected(status: Int) {
            when (status) {
                LoaderCallbackInterface.SUCCESS -> {
                    Log.i(TAG, "OpenCV успешно загружена.")
//                    System.loadLibrary("visible_guitar")
                    cameraBridge.enableView()
                }
                else -> {
                    super.onManagerConnected(status)
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_camera, container, false)
        activity!!.window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        ActivityCompat.requestPermissions(
            activity!!,
            arrayOf(Manifest.permission.CAMERA),
            CAMERA_PERMISSION_REQUEST
        )
//        setContentView(R.layout.activity_camera)
        cameraBridge = view.findViewById(R.id.camera1)

        cameraBridge.visibility = SurfaceView.VISIBLE
//        cameraBridge.scaleX = 2f;
//        cameraBridge.scaleY = 2f;
        cameraBridge.setCvCameraViewListener(this)

        return view
    }


//    override fun setupViews() {
//        activity!!.window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
//        ActivityCompat.requestPermissions(
//            activity!!,
//            arrayOf(Manifest.permission.CAMERA),
//            CAMERA_PERMISSION_REQUEST
//        )
////        activity!!.setContentView(R.layout.fragment_camera)
//        cameraBridge = viewBinding.camera1
//        cameraBridge.visibility = View.VISIBLE
////        val image = viewBinding.currentImage
//        cameraBridge.setCvCameraViewListener(this)
////        viewModel.updatedData.observe(this, { data ->
////            if (data.message == null) {
////                image.setImageBitmap(data.bitmap)
////            } else showBar(image, data.message)
////        })
//    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            CAMERA_PERMISSION_REQUEST -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    cameraBridge.setCameraPermissionGranted()
                } else {
                    val message = "Camera permission was not granted"
                    Log.e(TAG, message)
                    Toast.makeText(activity, message, Toast.LENGTH_LONG).show()
                }
            }
            else -> {
                Log.e(TAG, "Unexpected permission request")
            }
        }
    }

    override fun onPause() {
        super.onPause()
        cameraBridge.disableView()
    }

    override fun onResume() {
        super.onResume()
        if (!OpenCVLoader.initDebug()) {
            Log.d(TAG, "OpenCV НЕ установлена.")
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION, activity, loader)
        } else {
            Log.d(TAG, "OpenCV установлена.")
            loader.onManagerConnected(LoaderCallbackInterface.SUCCESS)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraBridge.disableView()
    }

    override fun onCameraViewStarted(width: Int, height: Int) {
        Log.e("HERE", "HERE")

        currentFrame = Mat(width, height, CvType.CV_8U)
    }

    override fun onCameraViewStopped() {
        Log.e("HERE", "HERE")

        cameraBridge.disableView()
    }

    override fun onCameraFrame(frame: CameraBridgeViewBase.CvCameraViewFrame): Mat {
        Log.e("HERE", "HERE")
        currentFrame = frame.rgba()
//        lifecycleScope.launch {
//            cameraViewModel.subscribeOnUpdate(currentFrame)
//        }
        return currentFrame
    }


}