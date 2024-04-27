package com.nafi.sfoods.data.datasource.menu

import com.nafi.sfoods.data.source.network.model.Menu.MenuResponse
import com.nafi.sfoods.data.source.network.model.order.CheckoutRequestPayload
import com.nafi.sfoods.data.source.network.model.order.CheckoutResponse

interface MenuDataSource {
    suspend fun getMenus(categoryParams: String?): MenuResponse

    suspend fun createOrder(payload: CheckoutRequestPayload): CheckoutResponse
}
