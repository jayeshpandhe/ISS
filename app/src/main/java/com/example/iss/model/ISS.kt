package com.example.iss.model

import com.google.gson.annotations.SerializedName

data class ISS(
    @SerializedName("timestamp")
    val timeStamp: Long,

    @SerializedName("iss_position")
    val position: Position,
)
