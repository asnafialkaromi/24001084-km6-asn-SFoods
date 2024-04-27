package com.nafi.sfoods.data.mapper

import com.nafi.sfoods.data.model.Menu
import com.nafi.sfoods.data.source.network.model.Menu.MenuItemResponse

fun MenuItemResponse?.toMenu() =
    Menu(
        imgUrl = this?.imageUrl.orEmpty(),
        name = this?.name.orEmpty(),
        location = this?.address.orEmpty(),
        rating = 4.6,
        price = this?.price ?: 0.0,
        description = this?.detail.orEmpty(),
        mapUrl = "",
    )

fun Collection<MenuItemResponse>?.toMenu() =
    this?.map {
        it.toMenu()
    } ?: listOf()
