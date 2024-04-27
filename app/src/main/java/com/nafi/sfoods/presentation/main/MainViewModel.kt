package com.nafi.sfoods.presentation.main

import androidx.lifecycle.ViewModel
import com.nafi.sfoods.data.repository.UserRepository

class MainViewModel(private val repository: UserRepository) : ViewModel() {
    fun isLoggedIn() = repository.isLoggedIn()
}
