package com.colourchangedemo.model.request

data class ImageRequest(
    var prompt:String,
    var n:Int,
    var size:String
)
