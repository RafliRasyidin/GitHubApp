package com.rasyidin.githubapp.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class UsersResponse(
    @field:SerializedName("items")
    val items: List<UserItemResponse>
)