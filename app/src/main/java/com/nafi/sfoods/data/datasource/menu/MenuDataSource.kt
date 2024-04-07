package com.nafi.sfoods.data.datasource.menu

import com.nafi.sfoods.data.source.network.model.Menu.MenuResponse

interface MenuDataSource {
    suspend fun getMenus(categoryParams : String?): MenuResponse
}