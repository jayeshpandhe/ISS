package com.example.iss.model

import com.google.gson.annotations.SerializedName

data class Astronaut(
    @SerializedName("craft")
    val craft: String,

    @SerializedName("name")
    val name: String,
)
