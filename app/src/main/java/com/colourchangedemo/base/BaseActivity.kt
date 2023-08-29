package com.colourchangedemo.base

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import java.util.*

open class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    fun getTimeFromAndroid() :String{
        val c = Calendar.getInstance()
        val timeOfDay = c[Calendar.HOUR_OF_DAY]

        Log.e("timeOfDay",timeOfDay.toString())
        var message="Hi"
        if (timeOfDay >= 0 && timeOfDay < 12) {
            message="Good Morning"
        } else if (timeOfDay >= 12 && timeOfDay < 16) {
            message="Good Afternoon"

        } else if (timeOfDay >= 16 && timeOfDay < 21) {
            message="Good Evening"

        } else if (timeOfDay >= 21 && timeOfDay < 24) {
            message="Good Night"

        }
        return message
    }
}