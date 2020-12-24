package com.digiapt.digiapttemplate.models

import com.google.gson.annotations.SerializedName

data class ContentModel(
    @SerializedName("id")
    val id: String,
    @SerializedName("title")
    val section_name: String
)