package com.nafi.sfoods.data.repository

import app.cash.turbine.test
import com.nafi.sfoods.data.datasource.auth.AuthDataSource
import com.nafi.sfoods.data.datasource.user.UserDataSource
import com.nafi.sfoods.utils.ResultWrapper
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class UserRepositoryImplTest {
    @MockK
    lateinit var userDataSource: UserDataSource

    @MockK
    lateinit var authDataSource: AuthDataSource

    private lateinit var repository: UserRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        repository = UserRepositoryImpl(authDataSource, userDataSource)
    }

    @Test
    fun `do login while loading`() {
        runTest {
            coEvery { authDataSource.doLogin(any(), any()) } returns true
            repository.doLogin("asjdha", "ashgdaksj").map {
                delay(100)
                it
            }.test {
                delay(110)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Loading)
                coVerify { authDataSource.doLogin(any(), any()) }
            }
        }
    }

    @Test
    fun `do login while success`() {
        runTest {
            coEvery { authDataSource.doLogin(any(), any()) } returns true
            repository.doLogin("asjdha", "ashgdaksj").map {
                delay(100)
                it
            }.test {
                delay(210)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Success)
                coVerify { authDataSource.doLogin(any(), any()) }
            }
        }
    }

    @Test
    fun `do login while error`() {
        runTest {
            coEvery { authDataSource.doLogin(any(), any()) } throws IllegalStateException("Error")
            repository.doLogin("asjdha", "ashgdaksj").map {
                delay(100)
                it
            }.test {
                delay(210)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Error)
                coVerify { authDataSource.doLogin(any(), any()) }
            }
        }
    }

    @Test
    fun `do register while loading`() {
        runTest {
            coEvery { authDataSource.doRegister(any(), any(), any()) } returns true
            repository.doRegister("kajsghda", "jasghdkas", "kjasdghaks").map {
                delay(100)
                it
            }.test {
                delay(110)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Loading)
                coVerify { authDataSource.doRegister(any(), any(), any()) }
            }
        }
    }

    @Test
    fun `do register while success`() {
        runTest {
            coEvery { authDataSource.doRegister(any(), any(), any()) } returns true
            repository.doRegister("kajsghda", "jasghdkas", "kjasdghaks").map {
                delay(100)
                it
            }.test {
                delay(210)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Success)
                coVerify { authDataSource.doRegister(any(), any(), any()) }
            }
        }
    }

    @Test
    fun `do register while error`() {
        runTest {
            coEvery { authDataSource.doRegister(any(), any(), any()) } throws IllegalStateException("Error")
            repository.doRegister("kajsghda", "jasghdkas", "kjasdghaks").map {
                delay(100)
                it
            }.test {
                delay(210)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Error)
                coVerify { authDataSource.doRegister(any(), any(), any()) }
            }
        }
    }

    @Test
    fun doLogout() {
        every { authDataSource.doLogout() } returns true
        val result = repository.doLogout()
        verify { authDataSource.doLogout() }
        assertEquals(true, result)
    }

    @Test
    fun isLoggedIn() {
        every { authDataSource.isLoggedIn() } returns true
        val result = repository.isLoggedIn()
        verify { authDataSource.isLoggedIn() }
        assertEquals(true, result)
    }

    @Test
    fun `get current user while is null`() {
        every { authDataSource.getCurrentUser() } returns null
        val result = repository.getCurrentUser()
        assertNull(result)
    }

    @Test
    fun `update profile while loading`() {
        runTest {
            coEvery { authDataSource.updateProfile(any(), any()) } returns true
            repository.updateProfile("asjdha").map {
                delay(100)
                it
            }.test {
                delay(110)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Loading)
                coVerify { authDataSource.updateProfile(any(), any()) }
            }
        }
    }

    @Test
    fun `update profile while success`() {
        runTest {
            coEvery { authDataSource.updateProfile(any(), any()) } returns true
            repository.updateProfile("asjdha").map {
                delay(100)
                it
            }.test {
                delay(210)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Success)
                coVerify { authDataSource.updateProfile(any(), any()) }
            }
        }
    }

    @Test
    fun `update profile while error`() {
        runTest {
            coEvery { authDataSource.updateProfile(any(), any()) } throws IllegalStateException("Error")
            repository.updateProfile("asjdha").map {
                delay(100)
                it
            }.test {
                delay(210)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Error)
                coVerify { authDataSource.updateProfile(any(), any()) }
            }
        }
    }

    @Test
    fun `update profile empty while loading`() {
        runTest {
            coEvery { authDataSource.updateProfile(any(), any()) } returns true
            repository.updateProfile().map {
                delay(100)
                it
            }.test {
                delay(110)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Loading)
                coVerify { authDataSource.updateProfile(any(), any()) }
            }
        }
    }

    @Test
    fun `update profile empty while success`() {
        runTest {
            coEvery { authDataSource.updateProfile(any(), any()) } returns true
            repository.updateProfile().map {
                delay(100)
                it
            }.test {
                delay(210)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Success)
                coVerify { authDataSource.updateProfile(any(), any()) }
            }
        }
    }

    @Test
    fun `update profile empty while error`() {
        runTest {
            coEvery { authDataSource.updateProfile(any(), any()) } throws IllegalStateException("Error")
            repository.updateProfile().map {
                delay(100)
                it
            }.test {
                delay(210)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Error)
                coVerify { authDataSource.updateProfile(any(), any()) }
            }
        }
    }

    @Test
    fun `update password while loading`() {
        runTest {
            coEvery { authDataSource.updatePassword(any()) } returns true
            repository.updatePassword("asjdha").map {
                delay(100)
                it
            }.test {
                delay(110)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Loading)
                coVerify { authDataSource.updatePassword(any()) }
            }
        }
    }

    @Test
    fun `update password while success`() {
        runTest {
            coEvery { authDataSource.updatePassword(any()) } returns true
            repository.updatePassword("asjdha").map {
                delay(100)
                it
            }.test {
                delay(210)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Success)
                coVerify { authDataSource.updatePassword(any()) }
            }
        }
    }

    @Test
    fun `update password while error`() {
        runTest {
            coEvery { authDataSource.updatePassword(any()) } throws IllegalStateException("Error")
            repository.updatePassword("asjdha").map {
                delay(100)
                it
            }.test {
                delay(210)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Error)
                coVerify { authDataSource.updatePassword(any()) }
            }
        }
    }

    @Test
    fun `update email while loading`() {
        runTest {
            coEvery { authDataSource.updateEmail(any()) } returns true
            repository.updateEmail("asjdha").map {
                delay(100)
                it
            }.test {
                delay(110)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Loading)
                coVerify { authDataSource.updateEmail(any()) }
            }
        }
    }

    @Test
    fun `update email while success`() {
        runTest {
            coEvery { authDataSource.updateEmail(any()) } returns true
            repository.updateEmail("asjdha").map {
                delay(100)
                it
            }.test {
                delay(210)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Success)
                coVerify { authDataSource.updateEmail(any()) }
            }
        }
    }

    @Test
    fun `update email while error`() {
        runTest {
            coEvery { authDataSource.updateEmail(any()) } throws IllegalStateException("Error")
            repository.updateEmail("asjdha").map {
                delay(100)
                it
            }.test {
                delay(210)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Error)
                coVerify { authDataSource.updateEmail(any()) }
            }
        }
    }

    @Test
    fun sendChangePasswordRequestByEmail() {
        runTest {
            coEvery { authDataSource.sendChangePasswordRequestByEmail() } returns true
            val result = repository.sendChangePasswordRequestByEmail()
            coVerify { authDataSource.sendChangePasswordRequestByEmail() }
            assertEquals(true, result)
        }
    }

    @Test
    fun isUsingGridMode() {
        every { userDataSource.isUsingGridMode() } returns true
        val result = repository.isUsingGridMode()
        verify { userDataSource.isUsingGridMode() }
        assertEquals(true, result)
    }

    @Test
    fun setUsingGridMode() {
        every { userDataSource.setUsingGridMode(any()) } returns Unit
        repository.setUsingGridMode(true)
        verify { userDataSource.setUsingGridMode(any()) }
    }
}
