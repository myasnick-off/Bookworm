package com.dev.miasnikoff.bookworm.core.network.model

import com.google.gson.annotations.SerializedName

data class VolumeResponse(
    @SerializedName("kind") val kind: String,
    @SerializedName("items") val volumes: List<VolumeDTO>,
    @SerializedName("totalItems") val totalItems: Int
)
