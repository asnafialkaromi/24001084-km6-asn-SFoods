package com.nafi.sfoods.data.model

import java.util.UUID

data class Menu(
    var id: String = UUID.randomUUID().toString(),
    var img: String,
    var name: String,
    var price: Double,
    var rating: Double,
    var description: String,
    var location: String,
    var mapUrl: String
)
