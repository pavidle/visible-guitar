package com.example.visible_guitar

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.SurfaceView
import android.widget.TextView
import androidx.core.content.ContextCompat
import org.opencv.android.BaseLoaderCallback
import org.opencv.android.CameraBridgeViewBase
import org.opencv.android.LoaderCallbackInterface
import org.opencv.android.OpenCVLoader
import org.opencv.core.Mat

class CameraActivity : AppCompatActivity(), CameraBridgeViewBase.CvCameraViewListener2 {

    companion object {
//        private const val REQUEST_CODE_CAMERA_PERMISSION = 101
        init {
            System.loadLibrary("visible_guitar")
        }
    }

    private lateinit var mOpenCvCameraView: CameraBridgeViewBase
    private lateinit var mIntermediateMat: Mat
    private val tag = CameraActivity::class.java.simpleName

    private val mLoaderCallback: BaseLoaderCallback = object : BaseLoaderCallback(this) {
        override fun onManagerConnected(status: Int) {
            when (status) {
                LoaderCallbackInterface.SUCCESS -> {
                    initializeCameraView()
                }
                else -> super.onManagerConnected(status)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initializeCameraView()


//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            when {
//                ContextCompat.checkSelfPermission(
//                    this,
//                    android.Manifest.permission.CAMERA
//                ) == PackageManager.PERMISSION_GRANTED -> {
//                    initializeCameraView()
//                }
//                shouldShowRequestPermissionRationale(android.Manifest.permission.CAMERA) -> {
//                }
//                else -> {
//                    requestPermissions(
//                        arrayOf(android.Manifest.permission.CAMERA),
//                        REQUEST_CODE_CAMERA_PERMISSION
//                    )
//                }
//            }
//        }
    }

//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<out String>,
//        grantResults: IntArray
//    ) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        if (requestCode == REQUEST_CODE_CAMERA_PERMISSION) {
//            val indexOfCameraPermission = permissions.indexOf(android.Manifest.permission.CAMERA)
//            if (indexOfCameraPermission != -1) {
//                if (grantResults.isNotEmpty()) {
//                    if (grantResults[indexOfCameraPermission] == PackageManager.PERMISSION_GRANTED) {
//                        Toast.makeText(
//                            applicationContext,
//                            "Camera permission granted!",
//                            Toast.LENGTH_LONG
//                        ).show()
//                        activateOpenCVCameraView()
//                    } else {
//                        Toast.makeText(
//                            applicationContext,
//                            "Camera permission is required to run this app!",
//                            Toast.LENGTH_LONG
//                        ).show()
//                    }
//                }
//            }
//        }
//    }

    private fun initializeCameraView() {
        mOpenCvCameraView = findViewById(R.id.camera)
        mOpenCvCameraView.setCameraPermissionGranted()
        mOpenCvCameraView.setCameraIndex(CameraBridgeViewBase.CAMERA_ID_ANY)
        mOpenCvCameraView.visibility = SurfaceView.VISIBLE
        mOpenCvCameraView.setCvCameraViewListener(this)
        mOpenCvCameraView.enableView()
    }


    override fun onResume() {
        super.onResume()
        if (ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            if (!OpenCVLoader.initDebug()) {
                Log.e(tag,"OpenCV НЕ был установлен.");
                OpenCVLoader.initAsync(
                    OpenCVLoader.OPENCV_VERSION_3_0_0, this,
                    mLoaderCallback
                )
            } else {
                Log.d(tag,"OpenCV был установлен.");
                mLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
            }
        }
    }

    override fun onCameraViewStarted(width: Int, height: Int) {
        mIntermediateMat = Mat()
    }

    override fun onCameraViewStopped() {
        mIntermediateMat.release()
    }

    override fun onCameraFrame(inputFrame: CameraBridgeViewBase.CvCameraViewFrame?): Mat {
        return inputFrame!!.gray()
    }

    override fun onDestroy() {
        mOpenCvCameraView.disableView()
        super.onDestroy()
    }

    
    external fun stringFromJNI(): String
}