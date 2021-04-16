package com.rasyidin.githubapp.core.domain.model

data class Repository(
    val name: String? = "",
    val reposUrl: String? = "",
    val description: String? = "",
    val starsCount: Int = 0,
    val watchersCount: Int = 0,
    val forksCount: Int = 0,
    val language: String? = ""
)