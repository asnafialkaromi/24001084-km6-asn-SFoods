package com.nafi.sfoods.data.source.network.model.Category

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class CategoryItemResponse(
    @SerializedName("image_url")
    val imageUrl: String?,
    @SerializedName("nama")
    val name: String?,
)
