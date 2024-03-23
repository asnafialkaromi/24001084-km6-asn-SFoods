package com.nafi.sfoods.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nafi.sfoods.data.datasource.CategoryDataSource
import com.nafi.sfoods.data.datasource.CategoryDataSourceImpl
import com.nafi.sfoods.data.datasource.MenuDataSource
import com.nafi.sfoods.data.datasource.MenuDataSourceImpl

class HomeViewModel: ViewModel() {

    private val dataSourceCategory: CategoryDataSource by lazy { CategoryDataSourceImpl() }
    private val dataSourceMenu: MenuDataSource by lazy { MenuDataSourceImpl() }

    private val _isUsingGridMode = MutableLiveData(false)

    val isUsingGridMode: LiveData<Boolean>
        get() = _isUsingGridMode

    fun changeListMode(){
        val currentValue = _isUsingGridMode.value ?: false
        _isUsingGridMode.postValue(!currentValue)
    }

    fun getCategoryList() = dataSourceCategory.getCategoryData()
    fun getMenuList() = dataSourceMenu.getMenuData()

}