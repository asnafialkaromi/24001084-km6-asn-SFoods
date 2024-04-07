package com.nafi.sfoods.data.source.network.model.Category


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class CategoryResponse(
    @SerializedName("code")
    val code: Int?,
    @SerializedName("data")
    val data: List<CategoryItemResponse>?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("status")
    val status: Boolean?
)