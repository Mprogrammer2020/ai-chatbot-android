package com.colourchangedemo.model

data class CreateImageResponse(
    val created: Int,
    val `data`: List<Data>
) {
    data class Data(
        val url: String
    )
}