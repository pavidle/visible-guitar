package com.example.visible_guitar.ui.activity

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import com.example.domain.ar.AugmentedReality
import com.example.visible_guitar.R
import com.example.visible_guitar.viewmodel.CameraViewModel
import dagger.hilt.android.AndroidEntryPoint
import org.opencv.android.*
import org.opencv.core.*
import org.opencv.imgproc.Imgproc


@AndroidEntryPoint
class CameraActivity : AppCompatActivity(), CameraBridgeViewBase.CvCameraViewListener2 {

    private lateinit var cameraBridge: CameraBridgeViewBase
    private val cameraViewModel by viewModels<CameraViewModel>()
    private lateinit var currentFrame: Mat

    companion object {
        private val TAG = CameraActivity::class.java.simpleName
        private const val CAMERA_PERMISSION_REQUEST = 1
        const val ID = "ID"
    }

    private lateinit var ar: AugmentedReality

    private val loader = object : BaseLoaderCallback(this) {
        override fun onManagerConnected(status: Int) {
            when (status) {
                LoaderCallbackInterface.SUCCESS -> {
                    Log.i(TAG, "OpenCV успешно загружена.")
//                    System.loadLibrary("visible_guitar")
                    cameraBridge.enableView()
                    ar = AugmentedReality.Builder().build()

                }
                else -> {
                    super.onManagerConnected(status)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        ActivityCompat.requestPermissions(
            this@CameraActivity,
            arrayOf(Manifest.permission.CAMERA),
            CAMERA_PERMISSION_REQUEST
        )
        setContentView(R.layout.activity_camera)
        cameraBridge = findViewById(R.id.camera)
        cameraBridge.setCvCameraViewListener(this)
        val image = findViewById<ImageView>(R.id.current_image)

//        cameraViewModel.updatedData.observe(this@CameraActivity) { data ->
////            val array = MatOfByte(data.bitmap).toArray()
////            val bitmap = BitmapFactory.decodeByteArray(array, 0, array.size)
////            image.setImageBitmap(bitmap)
//        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray,
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            CAMERA_PERMISSION_REQUEST -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    cameraBridge.setCameraPermissionGranted()
                } else {
                    val message = "Camera permission was not granted"
                    Log.e(TAG, message)
                    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
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
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION, this, loader)
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
        currentFrame = Mat(width, height, CvType.CV_8U)
    }

    override fun onCameraViewStopped() {
        cameraBridge.disableView()
    }


    override fun onCameraFrame(frame: CameraBridgeViewBase.CvCameraViewFrame): Mat {

        currentFrame = frame.gray()

        return ar.handle(currentFrame)
    }
}