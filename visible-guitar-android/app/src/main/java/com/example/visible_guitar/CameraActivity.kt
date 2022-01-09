package com.example.visible_guitar

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.SurfaceView
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.Toast
import androidx.core.app.ActivityCompat
import org.opencv.android.BaseLoaderCallback
import org.opencv.android.CameraBridgeViewBase
import org.opencv.android.LoaderCallbackInterface
import org.opencv.android.OpenCVLoader
import org.opencv.core.*


class CameraActivity : AppCompatActivity(), CameraBridgeViewBase.CvCameraViewListener2, View.OnTouchListener {

    private lateinit var cameraBridge: CameraBridgeViewBase
    private lateinit var currentFrame: Mat
    private var isClicked: Boolean = false

    companion object {
        private val TAG = CameraActivity::class.java.simpleName
        private const val CAMERA_PERMISSION_REQUEST = 1
    }

    private val loader = object : BaseLoaderCallback(this) {
        override fun onManagerConnected(status: Int) {
            when (status) {
                LoaderCallbackInterface.SUCCESS -> {
                    Log.i(TAG, "OpenCV успешно загружена.")
                    System.loadLibrary("visible_guitar")
                    cameraBridge.enableView()
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
        setContentView(R.layout.activity_main)
        cameraBridge = findViewById(R.id.camera)
        val begin = findViewById<Button>(R.id.begin)
        cameraBridge.visibility = SurfaceView.VISIBLE
        cameraBridge.scaleX = 1.4f;
        cameraBridge.scaleY = 1.4f;
        cameraBridge.setCvCameraViewListener(this)
        cameraBridge.setOnTouchListener(this)

        begin.setOnClickListener {
           isClicked = true
        }
    }

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
        currentFrame = frame.rgba()
        drawBoundingBox(currentFrame.nativeObjAddr)
        if (isClicked) {
            val mat = Mat()
            convertFrame(currentFrame.nativeObjAddr, mat.nativeObjAddr)
        }
        return currentFrame
    }

    private external fun drawBoundingBox(address: Long)
    private external fun addPoint(x: Int, y: Int)
    private external fun convertFrame(address: Long, out: Long)


    override fun onTouch(v: View, event: MotionEvent): Boolean {
        val cols = currentFrame.cols()
        val rows = currentFrame.rows()
        val xOffset = (cameraBridge.width - cols) / 2
        val yOffset = (cameraBridge.height - rows) / 2
        val x = event.x.toInt() - xOffset
        val y = event.y.toInt() - yOffset
        if (event.action == MotionEvent.ACTION_DOWN) {
            addPoint(x, y)
        }
        return false
    }
}