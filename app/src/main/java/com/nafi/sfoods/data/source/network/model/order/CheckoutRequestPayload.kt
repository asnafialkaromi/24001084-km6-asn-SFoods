package com.nafi.sfoods.data.source.network.model.order

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class CheckoutRequestPayload(
    @SerializedName("orders")
    val orders: List<CheckoutItemPayload?>?,
    @SerializedName("total")
    val total: Int?,
    @SerializedName("username")
    val username: String?,
)
