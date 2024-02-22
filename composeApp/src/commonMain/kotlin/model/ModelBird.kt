package model

import kotlinx.serialization.Serializable


@Serializable
data class ModelBird(
    val list: List<BirdImage> = emptyList()
)
