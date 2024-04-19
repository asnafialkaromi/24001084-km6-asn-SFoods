package com.nafi.sfoods.data.datasource.menu

import com.nafi.sfoods.data.source.network.model.Menu.MenuResponse
import com.nafi.sfoods.data.source.network.model.order.CheckoutRequestPayload
import com.nafi.sfoods.data.source.network.model.order.CheckoutResponse
import com.nafi.sfoods.data.source.network.services.SFoodsApiService

class MenuApiDataSource(private val service: SFoodsApiService) : MenuDataSource {
    override suspend fun getMenus(categoryParams : String?): MenuResponse {
        return service.getMenus(categoryParams)
    }

    override suspend fun createOrder(payload: CheckoutRequestPayload): CheckoutResponse {
        return service.createOrder(payload)
    }
}