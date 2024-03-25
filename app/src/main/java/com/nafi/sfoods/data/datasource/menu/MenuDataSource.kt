package com.nafi.sfoods.data.datasource.menu

import com.nafi.sfoods.data.model.Menu

interface MenuDataSource {
    fun getMenus(): List<Menu>
}