package com.example.drivefueki1012

/*

import android.Manifest
import android.content.ContentValues
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.Surface
import android.widget.Toast
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.lifecycle.LifecycleOwner
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


        if (!hasPermissions(applicationContext)) {

            ActivityCompat.requestPermissions(this, PERMISSION_REQUIRED, PERMISSIONS_REQUEST_CODE)
        }
        setContentView(R.layout.activity_main)

        appExecutor = ContextCompat.getMainExecutor(applicationContext)

        bindCameraUseCases()

        button1.setOnClickListener {
            val contentValues = ContentValues()
            contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, getFileName())
        }
        contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg")


        val outputOption = ImageCapture.OutputFileOptions.Builder(
            getContentResolver(),
            MediaStore.Images.Media.EXETERNAL_CONTENT_URI,
            contentValues
        ).build()
        imageCapture?.takePicture(outputOption, appExeCutor, imageSavedCB)

    }}


private fun bindCameraUseCases(){

    val cameraSelector=CameraSelector.Builder()
        .requireLensFacing(CameraSelector.LENS_FACING_BACK)
        .build()



    val cameraprovider= ProcessCameraProvider
        .getInstance(applicationContext)

    cameraProvider.addListener(Runnable {
        val cameraProvider = cameraProvider.get()

        preview = Preview.Builder()
            .setTargetAspectRatio(AspectRatio.RATIO_4_3)
            .setTargetRotation(Surface.ROTATION_0)
            .build()
        preview?.setSurfaceProvider(preview1.createSurfaceProvider(camera?.cameraInfo))


        imageCapture = ImageCapture.Builder()
            .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
            .setTargetAspectRatio(AspectRatio.RATIO_4_3)
            .setTargetRotation(Surface.ROTATION_0)
            .build()

        imageAnalyzer = ImageAnalysis.Builder()
            .setTargetAspectRatio(AspectRatio.RATIO_4_3)
            .setTargetRotation(Surface.ROTATION_0)
            .buid()

        cameraProvider.unbindAll()


        try {
            camera = cameraProvider.bindToLifecycle(
                this as LifecycleOwner, cameraSelector,
                preview, imageCapture, imageAnalyzer
            )
        } catch (exc: Exception) {
        }
    }, appExecutor)

}

override fun onRequestPermissionsResult(
    requestCode: Int, permissions: Array<String>,
    grantResults: IntArray){
    super.onRequestPermissionResult(requestCode,
        permissions, grantResults)
    if(requestCode== PERMISSION_REQUEST_CODE){
        if(PackageManager.PERMISSION_GRANTED==grantResults.firstOrNull()){
            Toast.makeText(applicationContext, "Permission granted!",
                Toast.LENGTH_SHORT).show()
        }else{

        }
    }
}







setContentView(R.layout.activity_camera_x)
}
}
*/