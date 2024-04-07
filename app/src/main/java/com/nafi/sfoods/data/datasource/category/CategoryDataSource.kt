package com.nafi.sfoods.data.datasource.category

import com.nafi.sfoods.data.source.network.model.Category.CategoryResponse

interface CategoryDataSource {
    suspend fun getCategories(): CategoryResponse
}
