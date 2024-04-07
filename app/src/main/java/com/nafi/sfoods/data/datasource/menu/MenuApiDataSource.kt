package com.nafi.sfoods.data.datasource.menu

import com.nafi.sfoods.data.source.network.model.Menu.MenuResponse
import com.nafi.sfoods.data.source.network.services.SFoodsApiService

class MenuApiDataSource(private val service: SFoodsApiService) : MenuDataSource {
    override suspend fun getMenus(categoryParams : String?): MenuResponse {
        return service.getMenus(categoryParams)
    }
}