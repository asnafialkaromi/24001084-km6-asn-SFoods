package com.nafi.sfoods.data.source.network.model.Category


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class CategoryItemResponse(
    @SerializedName("image_url")
    val imageUrl: String?,
    @SerializedName("nama")
    val name: String?
)