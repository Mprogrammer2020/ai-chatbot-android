package com.colourchangedemo.viewmodelfactory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.colourchangedemo.viewmodel.OpenAIViewModel

class OpenAIViewModelFactory(val baseActivity: Context) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        return if (modelClass.isAssignableFrom(OpenAIViewModel::class.java)) {
            OpenAIViewModel(baseActivity) as T
        }  else {
            throw IllegalArgumentException("Unknown class name")
        }

    }
}