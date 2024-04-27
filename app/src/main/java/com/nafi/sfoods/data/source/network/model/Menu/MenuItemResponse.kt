package com.nafi.sfoods.data.source.network.model.Menu

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class MenuItemResponse(
    @SerializedName("alamat_resto")
    val address: String?,
    @SerializedName("detail")
    val detail: String?,
    @SerializedName("harga")
    val price: Double?,
    @SerializedName("harga_format")
    val priceFormated: String?,
    @SerializedName("image_url")
    val imageUrl: String?,
    @SerializedName("nama")
    val name: String?,
)
