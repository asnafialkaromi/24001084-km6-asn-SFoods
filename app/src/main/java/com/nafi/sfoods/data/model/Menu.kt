package com.nafi.sfoods.data.model

import java.util.UUID

data class Menu(
    var id: String = UUID.randomUUID().toString(),
    var name: String,
    var price: Double,
    var rating: Double,
    var description: String,
    var mapUrl: String
)
