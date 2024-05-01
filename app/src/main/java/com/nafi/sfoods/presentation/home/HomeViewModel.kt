package com.nafi.sfoods.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.nafi.sfoods.data.repository.CategoryRepository
import com.nafi.sfoods.data.repository.MenuRepository
import com.nafi.sfoods.data.repository.UserRepository
import kotlinx.coroutines.Dispatchers

class HomeViewModel(
    private val categoryRepository: CategoryRepository,
    private val menuRepository: MenuRepository,
    private val userRepository: UserRepository,
) : ViewModel() {
    fun getCategories() = categoryRepository.getCategories().asLiveData(Dispatchers.IO)

    fun getMenus(categoryParams: String? = null) = menuRepository.getMenus(categoryParams).asLiveData(Dispatchers.IO)

    fun isUsingGridMode() = userRepository.isUsingGridMode()

    fun setUsingGridMode(isUsingGridMode: Boolean) = userRepository.setUsingGridMode(isUsingGridMode)

    fun getCurrentUser() = userRepository.getCurrentUser()
}
