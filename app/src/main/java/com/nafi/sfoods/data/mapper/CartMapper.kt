package com.nafi.sfoods.data.mapper

import com.nafi.sfoods.data.model.Cart
import com.nafi.sfoods.data.source.local.database.entity.CartEntity

fun Cart?.toCartEntity() = CartEntity(
    id = this?.id,
    menuId = this?.menuId.orEmpty(),
    menuImg = this?.menuImg.orEmpty(),
    menuName = this?.menuName.orEmpty(),
    menuPrice = this?.menuPrice ?: 0.0,
    itemQuantity = this?.itemQuantity ?: 0,
    itemNotes = this?.itemNotes
)

fun CartEntity?.toCart() = Cart(
    id = this?.id,
    menuId = this?.menuId.orEmpty(),
    menuImg = this?.menuImg.orEmpty(),
    menuName = this?.menuName.orEmpty(),
    menuPrice = this?.menuPrice ?: 0.0,
    itemQuantity = this?.itemQuantity ?: 0,
    itemNotes = this?.itemNotes
)

fun List<CartEntity>.toCartList() = this.map { it.toCart() }