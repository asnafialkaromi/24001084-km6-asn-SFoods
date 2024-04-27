package com.nafi.sfoods.data.source.network.model.order

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class CheckoutResponse(
    @SerializedName("code")
    val code: Int?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("status")
    val status: Boolean?,
)
