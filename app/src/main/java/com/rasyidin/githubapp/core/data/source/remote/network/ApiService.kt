package com.rasyidin.githubapp.core.data.source.remote.network

import com.rasyidin.githubapp.BuildConfig
import com.rasyidin.githubapp.core.data.source.remote.response.UserItemResponse
import com.rasyidin.githubapp.core.data.source.remote.response.UserRepositoryResponse
import com.rasyidin.githubapp.core.data.source.remote.response.UsersResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("/users")
    @Headers("Authorization: token ${BuildConfig.API_KEY}")
    suspend fun getUsers(): List<UserItemResponse>

    @GET("/users/{username}")
    @Headers("Authorization: token ${BuildConfig.API_KEY}")
    suspend fun getDetailUser(
        @Path("username") username: String?
    ): UserItemResponse

    @GET("/users/{username}/followers")
    @Headers("Authorization: token ${BuildConfig.API_KEY}")
    suspend fun getUserFollowers(
        @Path("username") username: String?
    ): List<UserItemResponse>

    @GET("/users/{username}/following")
    @Headers("Authorization: token ${BuildConfig.API_KEY}")
    suspend fun getUserFollowing(
        @Path("username") username: String?
    ): List<UserItemResponse>

    @GET("/search/users")
    @Headers("Authorization: token ${BuildConfig.API_KEY}")
    suspend fun searchUsers(
        @Query("q") query: String?
    ): UsersResponse

    @GET("users/{username}/repos")
    @Headers("Authorization: token ${BuildConfig.API_KEY}")
    suspend fun getUserRepos(
        @Path("username") username: String?
    ): List<UserRepositoryResponse>
}