package com.colourchangedemo.base

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate


class AppController : Application(){


    companion object {
        var mInstance: AppController? = null

         @Synchronized
        fun getInstance(): AppController? {
            return mInstance
        }
    }


    override fun onCreate() {
        super.onCreate()
        mInstance = this
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)


    }



}

