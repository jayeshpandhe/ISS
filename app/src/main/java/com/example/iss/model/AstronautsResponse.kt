package com.example.iss.model

import com.google.gson.annotations.SerializedName

data class AstronautsResponse(
    @SerializedName("number")
    val number: Int,

    @SerializedName("people")
    val people: List<Astronaut>
)
