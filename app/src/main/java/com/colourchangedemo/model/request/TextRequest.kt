package com.colourchangedemo.model.request

data class TextRequest(
    var model:String ="",
    var messages:ArrayList<RoleModel>
){
    data class RoleModel(
        var role:String="",
        var content:String=""
    )
}
