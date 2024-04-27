package com.nafi.sfoods.data.repository

import com.nafi.sfoods.data.datasource.cart.CartDataSource
import com.nafi.sfoods.data.mapper.toCartEntity
import com.nafi.sfoods.data.mapper.toCartList
import com.nafi.sfoods.data.model.Cart
import com.nafi.sfoods.data.model.Menu
import com.nafi.sfoods.data.model.PriceItem
import com.nafi.sfoods.data.source.local.database.entity.CartEntity
import com.nafi.sfoods.utils.ResultWrapper
import com.nafi.sfoods.utils.proceed
import com.nafi.sfoods.utils.proceedFlow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

interface CartRepository {
    fun getUserCartData(): Flow<ResultWrapper<Pair<List<Cart>, Double>>>

    fun getCheckoutData(): Flow<ResultWrapper<Triple<List<Cart>, List<PriceItem>, Double>>>

    fun createCart(
        menu: Menu,
        itemQuantity: Int,
        notes: String? = null,
    ): Flow<ResultWrapper<Boolean>>

    fun decreaseCart(item: Cart): Flow<ResultWrapper<Boolean>>

    fun increaseCart(item: Cart): Flow<ResultWrapper<Boolean>>

    fun setCartNotes(item: Cart): Flow<ResultWrapper<Boolean>>

    fun deleteCart(item: Cart): Flow<ResultWrapper<Boolean>>

    fun deleteAllCarts(): Flow<ResultWrapper<Boolean>>
}

class CartRepositoryImpl(private val cartDataSource: CartDataSource) : CartRepository {
    override fun getUserCartData(): Flow<ResultWrapper<Pair<List<Cart>, Double>>> {
        return cartDataSource.getAllCarts()
            .map {
                proceed {
                    val result = it.toCartList()
                    val totalPrice = result.sumOf { it.menuPrice * it.itemQuantity }
                    Pair(result, totalPrice)
                }
            }.map {
                if (it.payload?.first?.isEmpty() == false) return@map it
                ResultWrapper.Empty(it.payload)
            }.onStart {
                emit(ResultWrapper.Loading())
                delay(2000)
            }
    }

    override fun getCheckoutData(): Flow<ResultWrapper<Triple<List<Cart>, List<PriceItem>, Double>>> {
        return cartDataSource.getAllCarts()
            .map {
                proceed {
                    val result = it.toCartList()
                    val priceItemList = result.map { PriceItem(it.menuName, it.menuPrice * it.itemQuantity) }
                    val totalPrice = priceItemList.sumOf { it.total }
                    Triple(result, priceItemList, totalPrice)
                }
            }.map {
                if (it.payload?.first?.isEmpty() == false) return@map it
                ResultWrapper.Empty(it.payload)
            }.onStart {
                emit(ResultWrapper.Loading())
                delay(2000)
            }
    }

    override fun createCart(
        menu: Menu,
        itemQuantity: Int,
        notes: String?,
    ): Flow<ResultWrapper<Boolean>> {
        return menu.id?.let { menuId ->
            proceedFlow {
                val affectedRow =
                    cartDataSource.insertCart(
                        CartEntity(
                            menuId = menuId,
                            itemQuantity = itemQuantity,
                            itemNotes = notes,
                            menuImg = menu.imgUrl,
                            menuName = menu.name,
                            menuPrice = menu.price,
                        ),
                    )
                delay(2000)
                affectedRow > 0
            }
        } ?: flow {
            emit(ResultWrapper.Error(IllegalStateException("Menu Id is not found")))
        }
    }

    override fun increaseCart(item: Cart): Flow<ResultWrapper<Boolean>> {
        val modifiedCart =
            item.copy().apply {
                itemQuantity += 1
            }
        return proceedFlow { cartDataSource.updateCart(modifiedCart.toCartEntity()) > 0 }
    }

    override fun decreaseCart(item: Cart): Flow<ResultWrapper<Boolean>> {
        val modifiedCart =
            item.copy().apply {
                itemQuantity -= 1
            }
        return if (modifiedCart.itemQuantity <= 0) {
            proceedFlow { cartDataSource.deleteCart(item.toCartEntity()) > 0 }
        } else {
            proceedFlow { cartDataSource.updateCart(modifiedCart.toCartEntity()) > 0 }
        }
    }

    override fun setCartNotes(item: Cart): Flow<ResultWrapper<Boolean>> {
        return proceedFlow { cartDataSource.updateCart(item.toCartEntity()) > 0 }
    }

    override fun deleteCart(item: Cart): Flow<ResultWrapper<Boolean>> {
        return proceedFlow { cartDataSource.deleteCart(item.toCartEntity()) > 0 }
    }

    override fun deleteAllCarts(): Flow<ResultWrapper<Boolean>> {
        return proceedFlow {
            cartDataSource.deleteAll()
            true
        }
    }
}
