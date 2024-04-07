package com.nafi.sfoods.data.source.network.model.Menu


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class MenuResponse(
    @SerializedName("code")
    val code: Int?,
    @SerializedName("data")
    val data: List<MenuItemResponse>?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("status")
    val status: Boolean?
)