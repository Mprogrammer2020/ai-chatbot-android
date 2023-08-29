package com.colourchangedemo.flow.api

import com.colourchangedemo.model.request.ImageRequest
import com.colourchangedemo.model.request.ModerationRequest
import com.colourchangedemo.model.request.TextRequest
 import com.colourchangedemo.util.AppConstants
import com.google.gson.JsonObject
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST(AppConstants.CREATE_CHAT)
    suspend fun createChat(@Body jsonObject: TextRequest): JsonObject

    @POST(AppConstants.CREATE_IMAGE)
    suspend fun createImage(@Body jsonObject: ImageRequest): JsonObject

    @POST(AppConstants.CREATE_MODERATION)
    suspend fun createModeration(@Body jsonObject: ModerationRequest): JsonObject

}