package com.colourchangedemo.util

import android.app.Dialog
import android.app.DownloadManager
import android.content.ContentValues
import android.content.Context
import android.content.Context.DOWNLOAD_SERVICE
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.view.Gravity
import android.view.LayoutInflater
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.core.net.toUri
import androidx.work.*
import com.bumptech.glide.Glide
import com.colourchangedemo.R
import com.colourchangedemo.databinding.FullImageViewDialogBinding
 import java.io.File
import java.io.FileOutputStream
import java.net.URL
import java.util.*


object  Utility {

    fun fullImageShow(activity: Context,data:String){
        val dialog = Dialog(activity, R.style.ThemeDialog)
        val binding: FullImageViewDialogBinding = FullImageViewDialogBinding.inflate(LayoutInflater.from(activity))
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)

        dialog.setContentView(binding.root)

        dialog.window!!.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT
        )
        dialog.setCancelable(false)
        dialog.setCanceledOnTouchOutside(false)
        dialog.window?.setGravity(Gravity.BOTTOM)
        with(binding){

            ivFullView.setOnClickListener {
                dialog.dismiss()
            }


            Glide.with(activity).load(data).into(ivFullView)
        }

        dialog.show()

    }










}