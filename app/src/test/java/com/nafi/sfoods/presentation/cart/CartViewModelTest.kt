package com.nafi.sfoods.presentation.cart

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nafi.sfoods.data.repository.CartRepository
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
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class CartViewModelTest {
    @get:Rule
    val testRule: TestRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val coroutineRule: TestRule = MainCoroutineRule(UnconfinedTestDispatcher())

    @MockK
    lateinit var repository: CartRepository

    private lateinit var viewModel: CartViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel = spyk(CartViewModel(repository))
    }

    @Test
    fun getAllCarts() {
        every { repository.getUserCartData() } returns
            flow {
                emit(ResultWrapper.Success(Pair(listOf(mockk(relaxed = true), mockk(relaxed = true)), 80000.0)))
            }
        val result = viewModel.getAllCarts().getOrAwaitValue()
        assertEquals(2, result.payload?.first?.size)
        assertEquals(80000.0, result.payload?.second)
    }

    @Test
    fun increaseCart() {
        every { repository.increaseCart(any()) } returns
            flow {
                emit(ResultWrapper.Success(true))
            }
        viewModel.increaseCart(mockk())
        verify { repository.increaseCart(any()) }
    }

    @Test
    fun decreaseCart() {
        every { repository.decreaseCart(any()) } returns
            flow {
                emit(ResultWrapper.Success(true))
            }
        viewModel.decreaseCart(mockk())
        verify { repository.decreaseCart(any()) }
    }

    @Test
    fun removeCart() {
        every { repository.deleteCart(any()) } returns
            flow {
                emit(ResultWrapper.Success(true))
            }
        viewModel.removeCart(mockk())
        verify { repository.deleteCart(any()) }
    }

    @Test
    fun setCartNotes() {
        every { repository.setCartNotes(any()) } returns
            flow {
                emit(ResultWrapper.Success(true))
            }
        viewModel.setCartNotes(mockk())
        verify { repository.setCartNotes(any()) }
    }
}
