package com.nafi.sfoods.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.util.UUID

@Parcelize
data class Menu(
    var id : String? = UUID.randomUUID().toString(),
    var imgUrl: String,
    var name: String,
    var price: Double,
    var rating: Double,
    var description: String,
    var location: String,
    var mapUrl: String
): Parcelable
