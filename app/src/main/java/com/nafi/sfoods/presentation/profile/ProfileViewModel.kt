package com.nafi.sfoods.presentation.profile

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nafi.sfoods.data.repository.UserRepository
import com.nafi.sfoods.utils.ResultWrapper
import kotlinx.coroutines.launch

class ProfileViewModel(private val repository: UserRepository) : ViewModel() {
    private val _updateProfileResult = MutableLiveData<ResultWrapper<Boolean>>()
    val updateProfileResult: LiveData<ResultWrapper<Boolean>>
        get() = _updateProfileResult

    fun updateProfile(
        fullName: String?,
        photoUri: Uri?,
    ) {
        viewModelScope.launch {
            repository.updateProfile(fullName, photoUri).collect {
                _updateProfileResult.postValue(it)
            }
        }
    }

    fun getCurrentUser() = repository.getCurrentUser()
}
