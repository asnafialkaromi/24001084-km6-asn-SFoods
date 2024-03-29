package com.nafi.sfoods.data.model

data class Cart(
    var id: Int? = null,
    var menuId: String? = null,
    var menuImg: String,
    var menuName: String,
    var menuPrice: Double,
    var itemQuantity: Int,
    var itemNotes: String? = null
)
