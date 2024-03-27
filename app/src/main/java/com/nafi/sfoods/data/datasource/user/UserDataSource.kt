package com.nafi.sfoods.data.datasource.user

interface UserDataSource {
    fun isUsingGridMode() : Boolean
    fun setUsingGridMode(isUsingGridMode: Boolean)
}