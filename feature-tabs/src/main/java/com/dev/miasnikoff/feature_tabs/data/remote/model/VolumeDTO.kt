package com.dev.miasnikoff.feature_tabs.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VolumeDTO(
    @SerialName("id") val id: String,
    @SerialName("selfLink") val selfLink: String? = null,
    @SerialName("volumeInfo") val volumeInfo: VolumeInfoDTO,
    val isFavorite: Boolean = false
)
