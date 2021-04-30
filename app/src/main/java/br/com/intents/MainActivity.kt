package br.com.intents

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.AlarmClock
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private val requestCameraCode = 999
    private lateinit var imageView: ImageView
    private lateinit var buttonCamera : Button
    private lateinit var buttonClock : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
        setButtonsClick()
    }

    private fun initViews(){
        imageView = findViewById(R.id.imageView)
        buttonCamera = findViewById(R.id.buttonCamera)
        buttonClock = findViewById(R.id.buttonClock)
    }

    private fun setButtonsClick() {
        buttonCamera.setOnClickListener {
            openCamera()
        }

        buttonClock.setOnClickListener {
            openClock()
        }
    }

    private fun openCamera() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        try {
            startActivityForResult(takePictureIntent, requestCameraCode)
        } catch (e: ActivityNotFoundException) {
            Log.e("Error" ,"open camera exception: $e")
        }
    }

    private fun openClock(){
        val intent = Intent(AlarmClock.ACTION_SET_ALARM)
        try {
            startActivity(intent)
        } catch (e : ActivityNotFoundException) {
            Log.e("Error" ,"open clock exception: $e")
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == requestCameraCode && resultCode == Activity.RESULT_OK) {
            data?.extras?.let {
                val imageBitmap = it.get("data") as Bitmap
                imageView.setImageBitmap(imageBitmap)
            }
        }
    }
}