package com.nafi.sfoods.data.repository

import app.cash.turbine.test
import com.nafi.sfoods.data.datasource.category.CategoryDataSource
import com.nafi.sfoods.data.source.network.model.Category.CategoryItemResponse
import com.nafi.sfoods.data.source.network.model.Category.CategoryResponse
import com.nafi.sfoods.utils.ResultWrapper
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class CategoryRepositoryImplTest {
    @MockK
    lateinit var dataSource: CategoryDataSource

    private lateinit var repository: CategoryRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        repository = CategoryRepositoryImpl(dataSource)
    }

    @Test
    fun `get categories loading`() {
        val category1 =
            CategoryItemResponse(
                imageUrl = "asjkdghas",
                name = "ajsghdsjka",
            )
        val category2 =
            CategoryItemResponse(
                imageUrl = "asjkdghas",
                name = "ajsghdsjka",
            )
        val mockListCategory = listOf(category1, category2)
        val mockResponse = mockk<CategoryResponse>()
        every { mockResponse.data } returns mockListCategory
        runTest {
            coEvery { dataSource.getCategories() } returns mockResponse
            repository.getCategories().map {
                delay(100)
                it
            }.test {
                delay(110)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Loading)
                coVerify { dataSource.getCategories() }
            }
        }
    }

    @Test
    fun `get categories success`() {
        val category1 =
            CategoryItemResponse(
                imageUrl = "asjkdghas",
                name = "ajsghdsjka",
            )
        val category2 =
            CategoryItemResponse(
                imageUrl = "asjkdghas",
                name = "ajsghdsjka",
            )
        val mockListCategory = listOf(category1, category2)
        val mockResponse = mockk<CategoryResponse>()
        every { mockResponse.data } returns mockListCategory
        runTest {
            coEvery { dataSource.getCategories() } returns mockResponse
            repository.getCategories().map {
                delay(100)
                it
            }.test {
                delay(210)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Success)
                coVerify { dataSource.getCategories() }
            }
        }
    }

    @Test
    fun `get categories error`() {
        runTest {
            coEvery { dataSource.getCategories() } throws IllegalStateException("Error")
            repository.getCategories().map {
                delay(100)
                it
            }.test {
                delay(210)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Error)
                coVerify { dataSource.getCategories() }
            }
        }
    }

    @Test
    fun `get categories empty`() {
        val mockListCategory = listOf<CategoryItemResponse>()
        val mockResponse = mockk<CategoryResponse>()
        every { mockResponse.data } returns mockListCategory
        runTest {
            coEvery { dataSource.getCategories() } returns mockResponse
            repository.getCategories().map {
                delay(100)
                it
            }.test {
                delay(210)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Empty)
                coVerify { dataSource.getCategories() }
            }
        }
    }
}
