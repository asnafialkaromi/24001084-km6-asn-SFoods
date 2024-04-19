package com.nafi.sfoods.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nafi.sfoods.data.repository.UserRepository

class MainViewModel(private val repository: UserRepository) : ViewModel() {
    val isLoggedIn: Boolean
        get() = repository.isLoggedIn()
}