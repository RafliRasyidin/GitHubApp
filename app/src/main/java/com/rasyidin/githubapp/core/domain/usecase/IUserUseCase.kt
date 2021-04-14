package com.rasyidin.githubapp.core.domain.usecase

import android.database.Cursor
import com.rasyidin.githubapp.core.data.Resource
import com.rasyidin.githubapp.core.domain.model.User
import kotlinx.coroutines.flow.Flow

interface IUserUseCase {

    fun getUsers(): Flow<Resource<List<User>>>

    fun getDetailUser(username: String?): Flow<Resource<User>>

    fun getUserFollowers(username: String?): Flow<Resource<List<User>>>

    fun getUserFollowing(username: String?): Flow<Resource<List<User>>>

    suspend fun searchUsers(query: String?): Resource<List<User>>

    fun getFavoriteUsers(): Flow<List<User>>

    suspend fun insertFavorite(user: User)

    suspend fun deleteFavorite(user: User)

    suspend fun deleteFavoriteByUsername(username: String?)

    fun getFavoriteCursor(): Cursor
}