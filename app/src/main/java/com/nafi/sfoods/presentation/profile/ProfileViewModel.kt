package com.nafi.sfoods.presentation.profile

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nafi.sfoods.data.repository.UserRepository
import kotlinx.coroutines.launch

class ProfileViewModel(private val repository: UserRepository): ViewModel() {

    fun updateProfile(fullName: String?, photoUri: Uri?) {
        viewModelScope.launch {
            repository.updateProfile(fullName, photoUri)
        }
    }

    fun updateEmail(newEmail: String) {
        viewModelScope.launch {
            repository.updateEmail(newEmail)
        }
    }

}