package com.nafi.sfoods.data.datasource.menu

import com.nafi.sfoods.data.source.network.model.Menu.MenuResponse
import com.nafi.sfoods.data.source.network.model.order.CheckoutRequestPayload
import com.nafi.sfoods.data.source.network.model.order.CheckoutResponse
import com.nafi.sfoods.data.source.network.services.SFoodsApiService
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class MenuApiDataSourceTest {
    @MockK
    lateinit var service: SFoodsApiService

    private lateinit var dataSource: MenuDataSource

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        dataSource = MenuApiDataSource(service)
    }

    @Test
    fun getMenus() {
        runTest {
            val mockResponse = mockk<MenuResponse>(relaxed = true)
            coEvery { service.getMenus(any()) } returns mockResponse
            val actualResponse = dataSource.getMenus("makanan")
            coVerify { service.getMenus(any()) }
            assertEquals(mockResponse, actualResponse)
        }
    }

    @Test
    fun createOrder() {
        runTest {
            val mockRequest = mockk<CheckoutRequestPayload>(relaxed = true)
            val mockResponse = mockk<CheckoutResponse>(relaxed = true)
            coEvery { service.createOrder(any()) } returns mockResponse
            val actualResponse = dataSource.createOrder(mockRequest)
            coVerify { service.createOrder(any()) }
            assertEquals(mockResponse, actualResponse)
        }
    }
}
