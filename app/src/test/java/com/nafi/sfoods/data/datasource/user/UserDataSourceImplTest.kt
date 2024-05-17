package com.nafi.sfoods.data.datasource.user

import com.nafi.sfoods.data.source.local.pref.UserPreference
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class UserDataSourceImplTest {
    @MockK
    lateinit var prev: UserPreference

    private lateinit var dataSource: UserDataSource

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        dataSource = UserDataSourceImpl(prev)
    }

    @Test
    fun isUsingGridMode() {
        every { prev.isUsingGridMode() } returns true
        val result = dataSource.isUsingGridMode()
        verify { prev.isUsingGridMode() }
        assertEquals(true, result)
    }

    @Test
    fun setUsingGridMode() {
        every { prev.setUsingGridMode(any()) } returns Unit
        dataSource.setUsingGridMode(true)
        verify { prev.setUsingGridMode(any()) }
    }
}
