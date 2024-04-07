package com.nafi.sfoods.data.mapper

import com.nafi.sfoods.data.model.Category
import com.nafi.sfoods.data.source.network.model.Category.CategoryItemResponse

fun CategoryItemResponse?.toCategory() = Category(
    imgUrl = this?.imageUrl.orEmpty(),
    name = this?.name.orEmpty()
)

fun Collection<CategoryItemResponse>?.toCategories() =
    this?.map { it.toCategory() } ?: listOf()