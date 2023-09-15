package com.example.iss.model

import androidx.room.ColumnInfo
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.annotations.JsonAdapter
import com.google.gson.annotations.SerializedName
import java.lang.reflect.Type

data class Position(
    @SerializedName("latitude")
    @JsonAdapter(StringToDoubleJsonDeserializer::class)
    val latitude: Double,

    @SerializedName("longitude")
    @JsonAdapter(StringToDoubleJsonDeserializer::class)
    val longitude: Double,
)

/**
 * JSON deserializer to convert string to double
 */
class StringToDoubleJsonDeserializer: JsonDeserializer<Double> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Double {
        return json?.asDouble ?: 0.0
    }
}
