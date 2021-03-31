package com.rasyidin.githubapp.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class UserItemResponse(

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("avatar_url")
    val avatarUrl: String,

    @field:SerializedName("company")
    val company: String,

    @field:SerializedName("followers")
    val follower: Int,

    @field:SerializedName("following")
    val following: Int,

    @field:SerializedName("location")
    val location: String,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("public_repos")
    val repository: Int,

    @field:SerializedName("login")
    val username: String,

    @field:SerializedName("type")
    val type: String
)