package com.nafi.sfoods.presentation.detailmenu

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import com.nafi.sfoods.data.model.Menu
import com.nafi.sfoods.data.repository.CartRepository
import com.nafi.sfoods.databinding.ActivityDetailMenuBinding
import com.nafi.sfoods.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers

class DetailMenuViewModel(
    private val extras: Bundle?,
    private val cartRepository: CartRepository
): ViewModel() {

    private val menuData = extras?.getParcelable<Menu>(DetailMenuActivity.EXTRAS_DETAIL_DATA)

    val menuCounterLiveData = MutableLiveData(0).apply {
        postValue(1)
    }

    val menuPriceLiveData = MutableLiveData<Double>().apply {
        postValue(menuData?.price)
    }

    val mapUrlLiveData = MutableLiveData<String>().apply {
        postValue(menuData?.mapUrl)
    }

    fun plusCounter() {
        val counter = (menuCounterLiveData.value ?: 0) + 1
        menuCounterLiveData.postValue(counter)
        menuPriceLiveData.postValue(menuData?.price?.times(counter) ?: 0.0)
    }

    fun minusCounter() {
        if ((menuCounterLiveData.value ?: 0) > 1) {
            val counter = (menuCounterLiveData.value ?: 0) - 1
            menuCounterLiveData.postValue(counter)
            menuPriceLiveData.postValue(menuData?.price?.times(counter) ?: 0.0)
        }
    }

    fun addToCart() : LiveData<ResultWrapper<Boolean>>{
        return menuData?.let {
            val itemQuantity = menuCounterLiveData.value ?: 0
            cartRepository.createCart(it, itemQuantity).asLiveData(Dispatchers.IO)
        } ?: liveData { emit(ResultWrapper.Error(IllegalStateException("Menu tidak ditemukan"))) }
    }


}