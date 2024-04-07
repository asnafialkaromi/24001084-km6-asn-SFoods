package com.nafi.sfoods.data.source.network.services

import com.nafi.sfoods.BuildConfig
import com.nafi.sfoods.data.source.network.model.Category.CategoryResponse
import com.nafi.sfoods.data.source.network.model.Menu.MenuResponse
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface SFoodsApiService {
    @GET("category")
    suspend fun getCategories(): CategoryResponse

    @GET("listmenu")
    suspend fun getMenus(@Query("c") category: String? = null): MenuResponse

    companion object {
        @JvmStatic
        operator fun invoke(): SFoodsApiService {
            val okHttpClient = OkHttpClient.Builder()
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .build()
            val retrofit = Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
            return retrofit.create(SFoodsApiService::class.java)
        }
    }
}