package model

import kotlinx.serialization.Serializable

@Serializable
class BirdImage(
    var author: String,
    var category: String,
    var path: String
)
