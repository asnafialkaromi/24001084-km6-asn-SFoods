package com.nafi.sfoods.data.datasource.auth

import android.net.Uri
import com.google.firebase.Firebase
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import com.google.firebase.auth.userProfileChangeRequest
import kotlinx.coroutines.tasks.await

interface AuthDataSource {
    @Throws(exceptionClasses = [Exception::class])
    suspend fun doLogin(email: String, password: String): Boolean

    @Throws(exceptionClasses = [Exception::class])
    suspend fun doRegister(fullName: String, email: String, password: String): Boolean

    suspend fun updateProfile(
        fullName: String? = null,
        photoUri: Uri? = null
    ): Boolean

    suspend fun updatePassword(newPassword: String): Boolean

    suspend fun updateEmail(newEmail: String, password: String): Boolean

    fun sendChangePasswordRequestByEmail(): Boolean

    fun doLogout(): Boolean

    fun isLoggedIn(): Boolean

    fun getCurrentUser(): FirebaseUser?
}

class FirebaseAuthDataSourceImpl(private val firebaseAuth: FirebaseAuth) : AuthDataSource {
    @Throws(exceptionClasses = [Exception::class])
    override suspend fun doLogin(email: String, password: String): Boolean {
        val loginResult = firebaseAuth.signInWithEmailAndPassword(email, password).await()
        return loginResult.user != null
    }

    @Throws(exceptionClasses = [Exception::class])
    override suspend fun doRegister(fullName: String, email: String, password: String): Boolean {
        val registerResult = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
        registerResult.user?.updateProfile(
            userProfileChangeRequest {
                displayName = fullName
            }
        )?.await()
        return registerResult.user != null
    }

    override suspend fun updateProfile(
        fullName: String?,
        photoUri: Uri?
    ): Boolean {
        getCurrentUser()?.updateProfile(
            userProfileChangeRequest {
                fullName?.let { displayName = fullName }
                photoUri?.let { this.photoUri = it }
            }
        )?.await()
        return true
    }

    override suspend fun updatePassword(newPassword: String): Boolean {
        getCurrentUser()?.updatePassword(newPassword)?.await()
        return true
    }

    override suspend fun updateEmail(newEmail: String, password: String): Boolean {
        val currentUser = getCurrentUser() ?: return false // Check if user is authenticated
        try {
            val reAuthenticationResult = reAuthenticateCurrentUser(currentUser, password)
            if (!reAuthenticationResult) {
                return false
            } else {
                currentUser.verifyBeforeUpdateEmail(newEmail).await()
            }
            return true
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
    }

    private suspend fun reAuthenticateCurrentUser(currentUser: FirebaseUser, password: String): Boolean {
        return try {
            val credential = EmailAuthProvider.getCredential(currentUser.email!!,password)
            currentUser.reauthenticate(credential).await()
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    override fun sendChangePasswordRequestByEmail(): Boolean {
        getCurrentUser()?.email?.let { firebaseAuth.sendPasswordResetEmail(it) }
        return true
    }

    override fun getCurrentUser(): FirebaseUser? {
        return firebaseAuth.currentUser
    }

    override fun doLogout(): Boolean {
        Firebase.auth.signOut()
        return true
    }

    override fun isLoggedIn(): Boolean {
        return firebaseAuth.currentUser != null
    }

}