package com.nafi.sfoods.data.repository

import android.net.Uri
import com.nafi.sfoods.data.datasource.auth.AuthDataSource
import com.nafi.sfoods.data.datasource.user.UserDataSource
import com.nafi.sfoods.data.model.User
import com.nafi.sfoods.data.model.toUser
import com.nafi.sfoods.utils.ResultWrapper
import com.nafi.sfoods.utils.proceedFlow
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    suspend fun doLogin(email: String, password: String): Flow<ResultWrapper<Boolean>>

    suspend fun doRegister(
        fullName: String,
        email: String,
        password: String
    ): Flow<ResultWrapper<Boolean>>

    fun doLogout(): Boolean

    fun isLoggedIn(): Boolean

    fun getCurrentUser(): User?

    suspend fun updateProfile(
        fullName: String? = null,
        photoUri: Uri? = null
    ): Flow<ResultWrapper<Boolean>>

    suspend fun updatePassword(newPassword: String): Flow<ResultWrapper<Boolean>>

    suspend fun updateEmail(newEmail: String, password: String): Flow<ResultWrapper<Boolean>>

    fun sendChangePasswordRequestByEmail(): Boolean

    fun isUsingGridMode(): Boolean

    fun setUsingGridMode(isUsingGridMode: Boolean)
}

class UserRepositoryImpl(
    private val authDataSource: AuthDataSource,
    private val userDataSource: UserDataSource
) : UserRepository {
    override suspend fun doLogin(email: String, password: String): Flow<ResultWrapper<Boolean>> {
        return proceedFlow { authDataSource.doLogin(email, password) }
    }

    override suspend fun doRegister(
        fullName: String,
        email: String,
        password: String
    ): Flow<ResultWrapper<Boolean>> {
        return proceedFlow { authDataSource.doRegister(fullName, email, password) }
    }

    override fun doLogout(): Boolean {
        return authDataSource.doLogout()
    }

    override fun isLoggedIn(): Boolean {
        return authDataSource.isLoggedIn()
    }

    override fun getCurrentUser(): User? {
        return authDataSource.getCurrentUser().toUser()
    }

    override suspend fun updateProfile(
        fullName: String?,
        photoUri: Uri?
    ): Flow<ResultWrapper<Boolean>> {
        return proceedFlow { authDataSource.updateProfile(fullName, photoUri) }
    }

    override suspend fun updatePassword(newPassword: String): Flow<ResultWrapper<Boolean>> {
        return proceedFlow { authDataSource.updatePassword(newPassword) }
    }

    override suspend fun updateEmail(
        newEmail: String,
        password: String
    ): Flow<ResultWrapper<Boolean>> {
        return proceedFlow { authDataSource.updateEmail(newEmail, password) }
    }

    override fun sendChangePasswordRequestByEmail(): Boolean {
        return authDataSource.sendChangePasswordRequestByEmail()
    }

    override fun isUsingGridMode(): Boolean {
        return userDataSource.isUsingGridMode()
    }

    override fun setUsingGridMode(isUsingGridMode: Boolean) {
        return userDataSource.setUsingGridMode(isUsingGridMode)
    }
}

