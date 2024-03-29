package com.nafi.sfoods.presentation.checkout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.nafi.sfoods.data.repository.CartRepository
import kotlinx.coroutines.Dispatchers

class CheckoutViewModel( private val  cartRepository: CartRepository): ViewModel() {
    val checkoutData = cartRepository.getCheckoutData().asLiveData(Dispatchers.IO)
    val deleteAllCarts = cartRepository.deleteAllCarts().asLiveData(Dispatchers.IO)
}