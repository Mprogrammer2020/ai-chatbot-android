package com.colourchangedemo.model

data class CommonChatModel(

    var message:String="",

    var isSendMe:Boolean=false,
    var time:String="",
    var mediaType:String="" ,/*1 for text2 for image*/
    var images:ArrayList<String>?=null,
)
