package com.nafi.sfoods.data.datasource.auth

import android.net.Uri
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import com.nafi.sfoods.data.source.firebase.FirebaseService

interface AuthDataSource {
    @Throws(exceptionClasses = [Exception::class])
    suspend fun doLogin(
        email: String,
        password: String,
    ): Boolean

    @Throws(exceptionClasses = [Exception::class])
    suspend fun doRegister(
        fullName: String,
        email: String,
        password: String,
    ): Boolean

    suspend fun updateProfile(
        fullName: String? = null,
        photoUri: Uri? = null,
    ): Boolean

    suspend fun updatePassword(newPassword: String): Boolean

    suspend fun updateEmail(newEmail: String): Boolean

    suspend fun sendChangePasswordRequestByEmail(): Boolean

    fun doLogout(): Boolean

    fun isLoggedIn(): Boolean

    fun getCurrentUser(): FirebaseUser?
}

class FirebaseAuthDataSourceImpl(private val firebaseAuth: FirebaseService) : AuthDataSource {
    override suspend fun doLogin(
        email: String,
        password: String,
    ): Boolean {
        return firebaseAuth.doLogin(email, password)
    }

    override suspend fun doRegister(
        fullName: String,
        email: String,
        password: String,
    ): Boolean {
        return firebaseAuth.doRegister(fullName, email, password)
    }

    override suspend fun updateProfile(
        fullName: String?,
        photoUri: Uri?,
    ): Boolean {
        return firebaseAuth.updateProfile(fullName, photoUri)
    }

    override suspend fun updatePassword(newPassword: String): Boolean {
        return firebaseAuth.updatePassword(newPassword)
    }

    override suspend fun updateEmail(newEmail: String): Boolean {
        return firebaseAuth.updateEmail(newEmail)
    }

    override suspend fun sendChangePasswordRequestByEmail(): Boolean {
        return firebaseAuth.requestChangePasswordByEmail()
    }

    override fun doLogout(): Boolean {
        return firebaseAuth.doLogout()
    }

    override fun isLoggedIn(): Boolean {
        return firebaseAuth.isLoggedIn()
    }

    override fun getCurrentUser(): FirebaseUser? {
        return firebaseAuth.getCurrentUser()
    }
}
