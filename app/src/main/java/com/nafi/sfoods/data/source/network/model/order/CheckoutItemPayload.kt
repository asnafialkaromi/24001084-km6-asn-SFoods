package com.nafi.sfoods.data.source.network.model.order

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class CheckoutItemPayload(
    @SerializedName("catatan")
    val catatan: String?,
    @SerializedName("harga")
    val harga: Double?,
    @SerializedName("nama")
    val nama: String?,
    @SerializedName("qty")
    val qty: Int?,
)
