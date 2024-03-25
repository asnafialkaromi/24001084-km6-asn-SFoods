package com.nafi.sfoods.data.repository

import com.nafi.sfoods.data.datasource.menu.MenuDataSource
import com.nafi.sfoods.data.model.Menu


interface MenuRepository {
    fun getMenus(): List<Menu>
}

class MenuRepositoryImpl(private val dataSource: MenuDataSource): MenuRepository{
    override fun getMenus(): List<Menu> = dataSource.getMenus()

}