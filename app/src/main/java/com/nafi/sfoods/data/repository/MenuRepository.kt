package com.nafi.sfoods.data.repository

import com.nafi.sfoods.data.datasource.menu.MenuDataSource
import com.nafi.sfoods.data.mapper.toMenu
import com.nafi.sfoods.data.model.Menu
import com.nafi.sfoods.utils.ResultWrapper
import com.nafi.sfoods.utils.proceedFlow
import kotlinx.coroutines.flow.Flow


interface MenuRepository {
    fun getMenus(categoryParams: String? = null): Flow<ResultWrapper<List<Menu>>>
}

class MenuRepositoryImpl(private val dataSource: MenuDataSource) : MenuRepository {
    override fun getMenus(categoryParams: String?): Flow<ResultWrapper<List<Menu>>> {
        return proceedFlow { dataSource.getMenus(categoryParams).data.toMenu() }
    }
}