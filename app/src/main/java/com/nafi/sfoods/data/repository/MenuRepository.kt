package com.nafi.sfoods.data.repository

import com.nafi.sfoods.data.datasource.menu.MenuDataSource
import com.nafi.sfoods.data.mapper.toMenu
import com.nafi.sfoods.data.model.Cart
import com.nafi.sfoods.data.model.Menu
import com.nafi.sfoods.data.source.network.model.order.CheckoutItemPayload
import com.nafi.sfoods.data.source.network.model.order.CheckoutRequestPayload
import com.nafi.sfoods.utils.ResultWrapper
import com.nafi.sfoods.utils.proceedFlow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow


interface MenuRepository {
    fun getMenus(categoryParams: String? = null): Flow<ResultWrapper<List<Menu>>>

    fun createOrder(menu : List<Cart>) : Flow<ResultWrapper<Boolean>>
}

class MenuRepositoryImpl(private val dataSource: MenuDataSource) : MenuRepository {
    override fun getMenus(categoryParams: String?): Flow<ResultWrapper<List<Menu>>> {
        return proceedFlow { dataSource.getMenus(categoryParams).data.toMenu() }
    }

    override fun createOrder(menu: List<Cart>): Flow<ResultWrapper<Boolean>> {
        return proceedFlow {
            delay(2000)
            dataSource.createOrder(
                CheckoutRequestPayload(
                    total = null,
                    username = null,
                    orders = menu.map {
                        CheckoutItemPayload(
                            catatan = it.itemNotes,
                            harga = it.menuPrice,
                            nama = it.menuName,
                            qty = it.itemQuantity,
                        )
                    }
                )).status ?: false
        }
    }
}