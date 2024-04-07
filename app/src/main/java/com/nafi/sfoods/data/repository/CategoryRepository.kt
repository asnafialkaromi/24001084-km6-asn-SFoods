package com.nafi.sfoods.data.repository

import com.nafi.sfoods.data.datasource.category.CategoryDataSource
import com.nafi.sfoods.data.mapper.toCategories
import com.nafi.sfoods.data.model.Category
import com.nafi.sfoods.utils.ResultWrapper
import com.nafi.sfoods.utils.proceedFlow
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    fun getCategories(): Flow<ResultWrapper<List<Category>>>
}

class CategoryRepositoryImpl(private val dataSource: CategoryDataSource) : CategoryRepository {
    override fun getCategories(): Flow<ResultWrapper<List<Category>>> {
        return proceedFlow {
            dataSource.getCategories().data.toCategories()
        }
    }
}