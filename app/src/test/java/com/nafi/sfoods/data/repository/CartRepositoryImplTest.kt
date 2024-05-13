package com.nafi.sfoods.data.repository

import app.cash.turbine.test
import com.nafi.sfoods.data.datasource.cart.CartDataSource
import com.nafi.sfoods.data.model.Cart
import com.nafi.sfoods.data.model.Menu
import com.nafi.sfoods.data.source.local.database.entity.CartEntity
import com.nafi.sfoods.utils.ResultWrapper
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class CartRepositoryImplTest {
    @MockK
    lateinit var dataSource: CartDataSource

    private lateinit var repository: CartRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        repository = CartRepositoryImpl(dataSource)
    }

    @Test
    fun `get user cart data while loading`() {
        val entity1 =
            CartEntity(
                id = 1,
                menuId = "jkahdakwjd",
                menuImg = "akjdhaw",
                menuName = "ajghdakwj",
                menuPrice = 10000.0,
                itemQuantity = 2,
                itemNotes = "agsdjkawhd",
            )
        val entity2 =
            CartEntity(
                id = 1,
                menuId = "jkahdakwjd",
                menuImg = "akjdhaw",
                menuName = "ajghdakwj",
                menuPrice = 10000.0,
                itemQuantity = 2,
                itemNotes = "agsdjkawhd",
            )
        val mockList = listOf(entity1, entity2)
        val mockFlow =
            flow {
                emit(mockList)
            }
        every { dataSource.getAllCarts() } returns mockFlow
        runTest {
            repository.getUserCartData().map {
                delay(100)
                it
            }.test {
                delay(2110)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Loading)
                verify { dataSource.getAllCarts() }
            }
        }
    }

    @Test
    fun `get user cart data while success`() {
        val entity1 =
            CartEntity(
                id = 1,
                menuId = "jkahdakwjd",
                menuImg = "akjdhaw",
                menuName = "ajghdakwj",
                menuPrice = 10000.0,
                itemQuantity = 2,
                itemNotes = "agsdjkawhd",
            )
        val entity2 =
            CartEntity(
                id = 1,
                menuId = "jkahdakwjd",
                menuImg = "akjdhaw",
                menuName = "ajghdakwj",
                menuPrice = 10000.0,
                itemQuantity = 2,
                itemNotes = "agsdjkawhd",
            )
        val mockList = listOf(entity1, entity2)
        val mockFlow =
            flow {
                emit(mockList)
            }
        every { dataSource.getAllCarts() } returns mockFlow
        runTest {
            repository.getUserCartData().map {
                delay(100)
                it
            }.test {
                delay(2201)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Success)
                assertEquals(mockList.size, data.payload?.first?.size)
                assertEquals(40000.0, data.payload?.second)
                verify { dataSource.getAllCarts() }
            }
        }
    }

    @Test
    fun `get user cart data while error`() {
        every { dataSource.getAllCarts() } returns flow { throw IllegalStateException("Error") }
        runTest {
            repository.getUserCartData().map {
                delay(100)
                it
            }.test {
                delay(2201)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Error)
                verify { dataSource.getAllCarts() }
            }
        }
    }

    @Test
    fun `get user cart data while empty`() {
        val mockList = listOf<CartEntity>()
        val mockFlow =
            flow {
                emit(mockList)
            }
        every { dataSource.getAllCarts() } returns mockFlow
        runTest {
            repository.getUserCartData().map {
                delay(100)
                it
            }.test {
                delay(2201)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Empty)
                verify { dataSource.getAllCarts() }
            }
        }
    }

    @Test
    fun `get checkout data while loading`() {
        val entity1 =
            CartEntity(
                id = 1,
                menuId = "jkahdakwjd",
                menuImg = "akjdhaw",
                menuName = "ajghdakwj",
                menuPrice = 10000.0,
                itemQuantity = 2,
                itemNotes = "agsdjkawhd",
            )
        val entity2 =
            CartEntity(
                id = 1,
                menuId = "jkahdakwjd",
                menuImg = "akjdhaw",
                menuName = "ajghdakwj",
                menuPrice = 10000.0,
                itemQuantity = 2,
                itemNotes = "agsdjkawhd",
            )
        val mockList = listOf(entity1, entity2)
        val mockFlow =
            flow {
                emit(mockList)
            }
        every { dataSource.getAllCarts() } returns mockFlow
        runTest {
            repository.getCheckoutData().map {
                delay(100)
                it
            }.test {
                delay(2110)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Loading)
                verify { dataSource.getAllCarts() }
            }
        }
    }

    @Test
    fun `get checkout data while success`() {
        val entity1 =
            CartEntity(
                id = 1,
                menuId = "jkahdakwjd",
                menuImg = "akjdhaw",
                menuName = "ajghdakwj",
                menuPrice = 10000.0,
                itemQuantity = 2,
                itemNotes = "agsdjkawhd",
            )
        val entity2 =
            CartEntity(
                id = 1,
                menuId = "jkahdakwjd",
                menuImg = "akjdhaw",
                menuName = "ajghdakwj",
                menuPrice = 10000.0,
                itemQuantity = 2,
                itemNotes = "agsdjkawhd",
            )
        val mockList = listOf(entity1, entity2)
        val mockFlow =
            flow {
                emit(mockList)
            }
        every { dataSource.getAllCarts() } returns mockFlow
        runTest {
            repository.getCheckoutData().map {
                delay(100)
                it
            }.test {
                delay(2201)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Success)
                assertEquals(2, data.payload?.second?.size)
                assertEquals(40000.0, data.payload?.third)
                verify { dataSource.getAllCarts() }
            }
        }
    }

    @Test
    fun `get checkout data while error`() {
        every { dataSource.getAllCarts() } returns flow { throw IllegalStateException("Error") }
        runTest {
            repository.getCheckoutData().map {
                delay(100)
                it
            }.test {
                delay(2201)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Error)
                verify { dataSource.getAllCarts() }
            }
        }
    }

    @Test
    fun `get checkout data while empty`() {
        val mockList = listOf<CartEntity>()
        val mockFlow =
            flow {
                emit(mockList)
            }
        every { dataSource.getAllCarts() } returns mockFlow
        runTest {
            repository.getCheckoutData().map {
                delay(100)
                it
            }.test {
                delay(2201)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Empty)
                verify { dataSource.getAllCarts() }
            }
        }
    }

    @Test
    fun `create cart while loading`() {
        val mockMenu = mockk<Menu>(relaxed = true)
        every { mockMenu.id } returns "123"
        coEvery { dataSource.insertCart(any()) } returns 1
        runTest {
            repository.createCart(mockMenu, 2).map {
                delay(100)
                it
            }.test {
                delay(2110)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Loading)
                coVerify { dataSource.insertCart(any()) }
            }
        }
    }

    @Test
    fun `create cart while success`() {
        val mockMenu = mockk<Menu>(relaxed = true)
        every { mockMenu.id } returns "123"
        coEvery { dataSource.insertCart(any()) } returns 1
        runTest {
            repository.createCart(mockMenu, 2).map {
                delay(100)
                it
            }.test {
                delay(2201)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Success)
                assertEquals(true, data.payload)
                coVerify { dataSource.insertCart(any()) }
            }
        }
    }

    @Test
    fun `create cart while error processing`() {
        val mockMenu = mockk<Menu>(relaxed = true)
        every { mockMenu.id } returns "123"
        coEvery { dataSource.insertCart(any()) } throws IllegalStateException("error")
        runTest {
            repository.createCart(mockMenu, 2).map {
                delay(100)
                it
            }.test {
                delay(2201)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Error)
                coVerify { dataSource.insertCart(any()) }
            }
        }
    }

    @Test
    fun `create cart while error menu id null`() {
        val mockMenu = mockk<Menu>(relaxed = true)
        every { mockMenu.id } returns null
        coEvery { dataSource.insertCart(any()) } returns 1
        runTest {
            repository.createCart(mockMenu, 2).map {
                delay(100)
                it
            }.test {
                delay(2201)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Error)
                coVerify(atLeast = 0) { dataSource.insertCart(any()) }
            }
        }
    }

    @Test
    fun increaseCart() {
        val mockCart =
            Cart(
                id = 1,
                menuId = "jkahdakwjd",
                menuImg = "akjdhaw",
                menuName = "ajghdakwj",
                menuPrice = 10000.0,
                itemQuantity = 2,
                itemNotes = "agsdjkawhd",
            )
        coEvery { dataSource.updateCart(any()) } returns 1
        runTest {
            repository.increaseCart(mockCart).map {
                delay(100)
                it
            }.test {
                delay(2201)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Success)
                coVerify { dataSource.updateCart(any()) }
            }
        }
    }

    @Test
    fun `decrease cart while quantity more than 1`() {
        val mockCart =
            Cart(
                id = 1,
                menuId = "jkahdakwjd",
                menuImg = "akjdhaw",
                menuName = "ajghdakwj",
                menuPrice = 10000.0,
                itemQuantity = 2,
                itemNotes = "agsdjkawhd",
            )
        coEvery { dataSource.deleteCart(any()) } returns 1
        coEvery { dataSource.updateCart(any()) } returns 1
        runTest {
            repository.decreaseCart(mockCart).map {
                delay(100)
                it
            }.test {
                delay(2201)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Success)
                coVerify(atLeast = 0) { dataSource.deleteCart(any()) }
                coVerify(atLeast = 1) { dataSource.updateCart(any()) }
            }
        }
    }

    @Test
    fun `decrease cart while quantity less than 1`() {
        val mockCart =
            Cart(
                id = 1,
                menuId = "jkahdakwjd",
                menuImg = "akjdhaw",
                menuName = "ajghdakwj",
                menuPrice = 10000.0,
                itemQuantity = 1,
                itemNotes = "agsdjkawhd",
            )
        coEvery { dataSource.deleteCart(any()) } returns 1
        coEvery { dataSource.updateCart(any()) } returns 1
        runTest {
            repository.decreaseCart(mockCart).map {
                delay(100)
                it
            }.test {
                delay(2201)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Success)
                coVerify(atLeast = 1) { dataSource.deleteCart(any()) }
                coVerify(atLeast = 0) { dataSource.updateCart(any()) }
            }
        }
    }

    @Test
    fun setCartNotes() {
        val mockCart =
            Cart(
                id = 1,
                menuId = "jkahdakwjd",
                menuImg = "akjdhaw",
                menuName = "ajghdakwj",
                menuPrice = 10000.0,
                itemQuantity = 2,
                itemNotes = "agsdjkawhd",
            )
        coEvery { dataSource.updateCart(any()) } returns 1
        runTest {
            repository.setCartNotes(mockCart).map {
                delay(100)
                it
            }.test {
                delay(2201)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Success)
                coVerify(atLeast = 1) { dataSource.updateCart(any()) }
            }
        }
    }

    @Test
    fun deleteCart() {
        val mockCart =
            Cart(
                id = 1,
                menuId = "jkahdakwjd",
                menuImg = "akjdhaw",
                menuName = "ajghdakwj",
                menuPrice = 10000.0,
                itemQuantity = 2,
                itemNotes = "agsdjkawhd",
            )
        coEvery { dataSource.deleteCart(any()) } returns 1
        runTest {
            repository.deleteCart(mockCart).map {
                delay(100)
                it
            }.test {
                delay(2201)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Success)
                coVerify(atLeast = 1) { dataSource.deleteCart(any()) }
            }
        }
    }

    @Test
    fun deleteAllCarts() {
        coEvery { dataSource.deleteAll() } returns Unit
        runTest {
            repository.deleteAllCarts().map {
                delay(100)
                it
            }.test {
                delay(2201)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Success)
                coVerify { dataSource.deleteAll() }
            }
        }
    }
}
