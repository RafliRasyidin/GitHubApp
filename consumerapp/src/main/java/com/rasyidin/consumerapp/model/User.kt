package com.rasyidin.consumerapp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val id: Int = 0,
    val avatar: String? = "",
    val company: String? = "",
    val follower: Int = 0,
    val following: Int = 0,
    val location: String? = "",
    val name: String? = "",
    val repository: Int = 0,
    val username: String? = "",
    val type: String? = "",
    var isFavorite: Boolean = false
) : Parcelable