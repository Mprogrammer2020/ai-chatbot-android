package com.colourchangedemo.flow.api

import com.colourchangedemo.model.request.ImageRequest
import com.colourchangedemo.model.request.ModerationRequest
import com.colourchangedemo.model.request.TextRequest
import com.google.gson.JsonObject

import kotlinx.coroutines.flow.Flow

interface ApiHelper {

    fun createChat(map: TextRequest): Flow<JsonObject>
    fun createImage(map: ImageRequest): Flow<JsonObject>
    fun createModeration(map: ModerationRequest): Flow<JsonObject>



}