package com.rasyidin.githubapp.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class UserRepositoryResponse(
    @SerializedName("name")
    val name: String? = "",

    @SerializedName("html_url")
    val reposUrl: String? = "",

    @SerializedName("description")
    val description: String? = "",

    @SerializedName("stargazers_count")
    val starsCount: Int = 0,

    @SerializedName("watchers_count")
    val watchersCount: Int = 0,

    @SerializedName("forks_count")
    val forksCount: Int = 0,

    @SerializedName("language")
    val language: String? = ""
)