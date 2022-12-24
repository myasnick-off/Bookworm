package com.dev.miasnikoff.bookworm.data.model

import com.google.gson.annotations.SerializedName

data class VolumeResponse(
    @SerializedName("kind") val kind: String,
    @SerializedName("items") val volumes: List<VolumeDTO>,
    @SerializedName("totalItems") val totalItems: Int
)
