package com.nafi.sfoods.data.datasource.category

import com.nafi.sfoods.data.source.network.model.Category.CategoryResponse
import com.nafi.sfoods.data.source.network.services.SFoodsApiService

class CategoryApiDataSource(private val service : SFoodsApiService) : CategoryDataSource {
    override suspend fun getCategories(): CategoryResponse {
        return service.getCategories()
    }

}