package com.nafi.sfoods.presentation.home

import androidx.lifecycle.ViewModel
import com.nafi.sfoods.data.repository.CategoryRepository
import com.nafi.sfoods.data.repository.MenuRepository
import com.nafi.sfoods.data.repository.UserPreferenceRepository

class HomeViewModel(
    private val categoryRepository: CategoryRepository,
    private val menuRepository: MenuRepository,
) : ViewModel() {

    fun getCategories() = categoryRepository.getCategories()

    fun getMenus() = menuRepository.getMenus()
}