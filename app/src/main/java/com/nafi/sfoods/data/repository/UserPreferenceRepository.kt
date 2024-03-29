package com.nafi.sfoods.data.repository

import com.nafi.sfoods.data.source.local.pref.UserPreferenceImpl

interface UserPreferenceRepository {
    fun isUsingGridMode() : Boolean
    fun setUsingGridMode(isUsingGridMode: Boolean)
}

class UserPreferenceRepositoryImpl(private val userDataSource: UserPreferenceImpl) : UserPreferenceRepository{
    override fun isUsingGridMode(): Boolean {
        return userDataSource.isUsingGridMode()
    }

    override fun setUsingGridMode(isUsingGridMode: Boolean) {
        userDataSource.setUsingGridMode(isUsingGridMode)
    }

}