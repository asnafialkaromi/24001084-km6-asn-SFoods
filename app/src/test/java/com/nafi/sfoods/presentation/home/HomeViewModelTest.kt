package com.nafi.sfoods.presentation.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nafi.sfoods.data.model.User
import com.nafi.sfoods.data.repository.CategoryRepository
import com.nafi.sfoods.data.repository.MenuRepository
import com.nafi.sfoods.data.repository.UserRepository
import com.nafi.sfoods.tools.MainCoroutineRule
import com.nafi.sfoods.tools.getOrAwaitValue
import com.nafi.sfoods.utils.ResultWrapper
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class HomeViewModelTest {
    @get:Rule
    val testRule: TestRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val coroutineRule: TestRule = MainCoroutineRule(UnconfinedTestDispatcher())

    @MockK
    lateinit var categoryRepository: CategoryRepository

    @MockK
    lateinit var menuRepository: MenuRepository

    @MockK
    lateinit var userRepository: UserRepository

    private lateinit var viewModel: HomeViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel =
            spyk(
                HomeViewModel(
                    categoryRepository, menuRepository, userRepository,
                ),
            )
    }

    @Test
    fun getCategories() {
        every { categoryRepository.getCategories() } returns
            flow {
                emit(ResultWrapper.Success(listOf(mockk(), mockk())))
            }
        val result = viewModel.getCategories().getOrAwaitValue()
        assertEquals(2, result.payload?.size)
        verify { categoryRepository.getCategories() }
    }

    @Test
    fun getMenus() {
        every { menuRepository.getMenus() } returns
            flow {
                emit(ResultWrapper.Success(listOf(mockk(), mockk())))
            }
        val result = viewModel.getMenus().getOrAwaitValue()
        assertEquals(2, result.payload?.size)
        verify { menuRepository.getMenus() }
    }

    @Test
    fun isUsingGridMode() {
        every { userRepository.isUsingGridMode() } returns true
        val result = viewModel.isUsingGridMode()
        assertEquals(true, result)
        verify { userRepository.isUsingGridMode() }
    }

    @Test
    fun setUsingGridMode() {
        every { userRepository.setUsingGridMode(any()) } returns Unit
        viewModel.setUsingGridMode(true)
        verify { userRepository.setUsingGridMode(any()) }
    }

    @Test
    fun getCurrentUser() {
        val mockResult = mockk<User>()
        every { userRepository.getCurrentUser() } returns mockResult
        val result = viewModel.getCurrentUser()
        assertEquals(mockResult, result)
        verify { userRepository.getCurrentUser() }
    }
}
