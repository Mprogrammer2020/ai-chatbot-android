package com.colourchangedemo.flow.api

import com.colourchangedemo.model.request.ImageRequest
import com.colourchangedemo.model.request.ModerationRequest
import com.colourchangedemo.model.request.TextRequest
import kotlinx.coroutines.flow.flow

class ApiHelperImpl(private val apiService: ApiService) : ApiHelper {

    override fun createChat(map: TextRequest) = flow { emit(apiService.createChat(map)) }
    override fun createImage(map: ImageRequest) = flow { emit(apiService.createImage(map)) }
    override fun createModeration(map: ModerationRequest) = flow { emit(apiService.createModeration(map)) }


}