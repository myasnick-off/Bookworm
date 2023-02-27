package com.dev.miasnikoff.bookworm.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VolumeResponse(
    @SerialName("kind") val kind: String,
    @SerialName("items") val volumes: List<VolumeDTO>? = null,
    @SerialName("totalItems") val totalItems: Int
)
