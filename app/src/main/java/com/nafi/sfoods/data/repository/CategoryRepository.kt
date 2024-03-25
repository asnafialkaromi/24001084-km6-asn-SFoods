package com.nafi.sfoods.data.repository

import com.nafi.sfoods.data.datasource.category.CategoryDataSource
import com.nafi.sfoods.data.model.Category

interface CategoryRepository {
    fun getCategories(): List<Category>
}

class CategoryRepositoryImpl(private val dataSource: CategoryDataSource): CategoryRepository {
    override fun getCategories(): List<Category> = dataSource.getCategories()
}