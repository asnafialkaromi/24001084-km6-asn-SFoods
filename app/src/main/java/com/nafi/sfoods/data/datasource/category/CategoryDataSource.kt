package com.nafi.sfoods.data.datasource.category

import com.nafi.sfoods.data.model.Category

interface CategoryDataSource {
    fun getCategories(): List<Category>
}