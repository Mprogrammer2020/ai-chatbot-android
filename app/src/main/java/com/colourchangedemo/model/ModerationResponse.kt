package com.colourchangedemo.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

open class ModerationResponse {
    @SerializedName("id")
    @Expose
    var id: String? = null

    @SerializedName("model")
    @Expose
    var model: String? = null

    @SerializedName("results")
    @Expose
    var results: List<Result>? = null

    open inner class Categories {
        @SerializedName("hate")
        @Expose
        var hate: Boolean? = false

        @SerializedName("hate/threatening")
        @Expose
        var hateThreatening: Boolean? = null

        @SerializedName("self-harm")
        @Expose
        var selfHarm: Boolean? = null

        @SerializedName("sexual")
        @Expose
        var sexual: Boolean? = null

        @SerializedName("sexual/minors")
        @Expose
        var sexualMinors: Boolean? = null

        @SerializedName("violence")
        @Expose
        var violence: Boolean? = null

        @SerializedName("violence/graphic")
        @Expose
        var violenceGraphic: Boolean? = null
    }

    open inner class CategoryScores {
        @SerializedName("hate")
        @Expose
        var hate: Double? = null

        @SerializedName("hate/threatening")
        @Expose
        var hateThreatening: Double? = null

        @SerializedName("self-harm")
        @Expose
        var selfHarm: Double? = null

        @SerializedName("sexual")
        @Expose
        var sexual: Double? = null

        @SerializedName("sexual/minors")
        @Expose
        var sexualMinors: Double? = null

        @SerializedName("violence")
        @Expose
        var violence: Double? = null

        @SerializedName("violence/graphic")
        @Expose
        var violenceGraphic: Double? = null
    }

    open inner class Result {
        @SerializedName("categories")
        @Expose
        var categories: Categories? = null

        @SerializedName("category_scores")
        @Expose
        var categoryScores: CategoryScores? = null

        @SerializedName("flagged")
        @Expose
        var flagged: Boolean? = null
    }
}