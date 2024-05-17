package com.nafi.sfoods.data.datasource.auth

import android.net.Uri
import com.google.firebase.auth.FirebaseUser
import com.nafi.sfoods.data.source.firebase.FirebaseService
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class FirebaseAuthDataSourceImplTest {
    @MockK
    lateinit var service: FirebaseService

    private lateinit var dataSource: AuthDataSource

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        dataSource = FirebaseAuthDataSourceImpl(service)
    }

    @Test
    fun doLogin() {
        runTest {
            val email: String = "asnafialkaromi@gmail.com"
            val password: String = "qwerty123"
            coEvery { service.doLogin(any(), any()) } returns true
            val result = dataSource.doLogin(email, password)
            coVerify { service.doLogin(any(), any()) }
            assertEquals(true, result)
        }
    }

    @Test
    fun doRegister() {
        runTest {
            val name: String = "Asnafi"
            val email: String = "asnafialkaromi@gmail.com"
            val password: String = "qwerty123"
            coEvery { service.doRegister(any(), any(), any()) } returns true
            val result = dataSource.doRegister(name, email, password)
            coVerify { service.doRegister(any(), any(), any()) }
            assertEquals(true, result)
        }
    }

    @Test
    fun `update profile while changed`() {
        runTest {
            val name: String = "Asnafi"
            val photo: Uri? = null
            coEvery { service.updateProfile(any()) } returns true
            val result = dataSource.updateProfile(name, photo)
            coVerify { service.updateProfile(any()) }
            assertEquals(true, result)
        }
    }

    @Test
    fun `update profile while is null`() {
        runTest {
            coEvery { service.updateProfile(any()) } returns true
            val result = dataSource.updateProfile()
            coVerify { service.updateProfile(any()) }
            assertEquals(true, result)
        }
    }

    @Test
    fun updatePassword() {
        runTest {
            coEvery { service.updatePassword(any()) } returns true
            val result = dataSource.updatePassword("asdghkasdga")
            coVerify { service.updatePassword(any()) }
            assertEquals(true, result)
        }
    }

    @Test
    fun updateEmail() {
        runTest {
            coEvery { service.updateEmail(any()) } returns true
            val result = dataSource.updateEmail("ajghdajksd")
            coVerify { service.updateEmail(any()) }
            assertEquals(true, result)
        }
    }

    @Test
    fun sendChangePasswordRequestByEmail() {
        runTest {
            coEvery { service.requestChangePasswordByEmail() } returns true
            val result = dataSource.sendChangePasswordRequestByEmail()
            coEvery { service.requestChangePasswordByEmail() }
            assertEquals(true, result)
        }
    }

    @Test
    fun doLogout() {
        every { service.doLogout() } returns true
        val result = dataSource.doLogout()
        verify { service.doLogout() }
        assertEquals(true, result)
    }

    @Test
    fun isLoggedIn() {
        every { service.isLoggedIn() } returns true
        val result = dataSource.isLoggedIn()
        verify { service.isLoggedIn() }
        assertEquals(true, result)
    }

    @Test
    fun getCurrentUser() {
        val mockFirebaseUser = mockk<FirebaseUser>()
        every { service.getCurrentUser() } returns mockFirebaseUser
        val result = dataSource.getCurrentUser()
        verify { service.getCurrentUser() }
        assertEquals(mockFirebaseUser, result)
    }
}
