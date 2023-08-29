package com.colourchangedemo.model

data class ChatCompletionResponse(
    val choices: List<Choice>,
    val created: Int,
    val id: String,
    val model: String,
    val `object`: String,
    val usage: Usage
){
    data class Choice(
        val finish_reason: String,
        val index: Int,
        val message: Message
    )

    data class ImageModel(

        var model:String,
        var messages:ArrayList<RoleModel>
    ){
        class RoleModel(
            var role:String ="",
            var content:String=""
        )
    }

    data class Message(
        val content: String,
        val role: String
    )

    data class Usage(
        val completion_tokens: Int,
        val prompt_tokens: Int,
        val total_tokens: Int
    )



}