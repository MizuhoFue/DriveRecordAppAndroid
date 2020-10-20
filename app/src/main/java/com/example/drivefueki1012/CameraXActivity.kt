package com.example.drivefueki1012

import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.camera.core.*
import java.util.concurrent.Executor


private const val PERMISSION_REQUEST_CODE=1234

private val PERMISSION_REQUIRED=arrayOf(

    Manifest.permission.CAMERA,
    Manifest.permission.WRITE_EXTERNAL_STORAGE

)

class CameraXActivity : AppCompatActivity() {
    private lateinit var appExecutor:Executor
    private var preview: Preview?=null
    private var imageCapture:ImageCapture?=null
    private var camera:Camera?=null
    private var imageAnalyzer: ImageAnalysis?=null


    private val ImageSavedCB=
        object : ImageCapture.OnImageSavedCallback{

            override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                Toast.makeText(applicationContext,"save to file.", Toast.LENGTH_LONG).show()
            }

            override fun onError(exception: ImageCaptureException) {}

        }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        /*if (!hasPermissions(applicationContext)){

            ActivityCompat.requestPermissions
        }*/





        setContentView(R.layout.activity_camera_x)
    }
}