package com.nafi.sfoods.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.nafi.sfoods.data.repository.CategoryRepository
import com.nafi.sfoods.data.repository.MenuRepository
import com.nafi.sfoods.data.repository.UserPreferenceRepository
import kotlinx.coroutines.Dispatchers

class HomeViewModel(
    private val categoryRepository: CategoryRepository,
    private val menuRepository: MenuRepository,
) : ViewModel() {

    fun getCategories() = categoryRepository.getCategories().asLiveData(Dispatchers.IO)

    fun getMenus(categoryParams : String? = null) = menuRepository.getMenus(categoryParams).asLiveData(Dispatchers.IO)
}