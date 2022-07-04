package com.example.visible_guitar.ui.activity

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.domain.ar.NeckLabelsDrawer
import com.example.domain.ar.NeckMeshDrawerWrapper
//import com.example.domain.ar.ArucoAugmentedReality
import com.example.domain.ar.base.AugmentedReality
import com.example.domain.ar.base.Drawer
import com.example.domain.ar.generator.NeckMesh
import com.example.domain.ar.model.LabelNote
import org.opencv.android.BaseLoaderCallback
import org.opencv.android.CameraBridgeViewBase
import org.opencv.android.LoaderCallbackInterface
import org.opencv.android.OpenCVLoader
import org.opencv.core.CvType
import org.opencv.core.Mat
import org.opencv.core.Size

abstract class BaseCameraActivity(
    @LayoutRes private val layoutId: Int,
    private val cameraId: Int
) : AppCompatActivity(), CameraBridgeViewBase.CvCameraViewListener2 {

    protected lateinit var cameraBridge: CameraBridgeViewBase
    protected lateinit var currentFrame: Mat


//    protected lateinit var arucoAugmentedReality: AugmentedReality

    private val fpsMeter = FpsMeter(this)
    private companion object {
        private const val CAMERA_PERMISSION_REQUEST = 1
        private var CAMERA_INDEX = 1;
    }

    private val loader = object : BaseLoaderCallback(this) {
        override fun onManagerConnected(status: Int) {
            when (status) {
                LoaderCallbackInterface.SUCCESS -> {
                    Log.i("OPENCV", "OpenCV успешно загружена.")
                    cameraBridge.enableView()

//                    arucoAugmentedReality = ArucoAugmentedReality.Builder().build()
                }
                else -> {
                    super.onManagerConnected(status)
                }
            }
        }
    }

    protected abstract fun setupListeners()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.CAMERA),
            CAMERA_PERMISSION_REQUEST
        )
        setContentView(layoutId)
        cameraBridge = findViewById(cameraId)
        cameraBridge.setCameraIndex(CAMERA_INDEX)
        cameraBridge.setCvCameraViewListener(this)
        setupListeners()
    }

    protected fun swapCamera() {
        val id = CAMERA_INDEX xor 1
        cameraBridge.disableView()
        cameraBridge.setCameraIndex(id)
        cameraBridge.enableView()
        CAMERA_INDEX = id
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
                    Log.e("OPENCV", message)
                    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
                }
            }
            else -> {
                Log.e("OPENCV", "Unexpected permission request")
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
            Log.d("OPENCV", "OpenCV НЕ установлена.")
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION, this, loader)
        } else {
            Log.d("OPENCV", "OpenCV установлена.")
            loader.onManagerConnected(LoaderCallbackInterface.SUCCESS)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraBridge.disableView()
    }

    protected abstract fun setupCameraThread()

    override fun onCameraViewStarted(width: Int, height: Int) {
        currentFrame = Mat(height, width, CvType.CV_8UC4)
        setupCameraThread()

    }

    override fun onCameraViewStopped() {
        cameraBridge.disableView()
    }


    protected fun setFpsLabel(textView: TextView) {
        fpsMeter.setFps(textView)
    }

    override fun onCameraFrame(inputFrame: CameraBridgeViewBase.CvCameraViewFrame): Mat =
        handleFrame(inputFrame)

    protected abstract fun handleFrame(inputFrame: CameraBridgeViewBase.CvCameraViewFrame): Mat
}