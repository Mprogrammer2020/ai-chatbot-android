package com.colourchangedemo.util

import android.app.Dialog
import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.Window
import android.view.WindowManager
import com.bumptech.glide.Glide
import com.colourchangedemo.R
import com.colourchangedemo.databinding.FullImageViewDialogBinding


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