package com.rasyidin.githubapp.core.data.source.remote.response

data class Users(
    val avatar: String? = "",
    val company: String? = "",
    val follower: Int? = 0,
    val following: Int? = 0,
    val location: String? = "",
    val name: String? = "",
    val repository: Int? = 0,
    val username: String? = ""
)