package com.nafi.sfoods.presentation.checkout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.nafi.sfoods.data.repository.CartRepository
import com.nafi.sfoods.data.repository.MenuRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CheckoutViewModel( private val  cartRepository: CartRepository, private val menuRepository: MenuRepository): ViewModel() {
    val checkoutData = cartRepository.getCheckoutData().asLiveData(Dispatchers.IO)

    fun deleteAllCarts() {
        viewModelScope.launch {
            cartRepository.deleteAllCarts().collect{

            }
        }
    }
    fun checkoutCart() = menuRepository.createOrder(checkoutData.value?.payload?.first.orEmpty()).asLiveData(Dispatchers.IO)
}