package com.colourchangedemo.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.colourchangedemo.databinding.ActivitySplashScreenBinding


class SplashScreen : AppCompatActivity() {
    lateinit var binding:ActivitySplashScreenBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Handler(Looper.getMainLooper()).postDelayed(
            {
            startActivity(Intent(this@SplashScreen, OpenAISampleActivity::class.java))
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out)
        },2000)

    }


}