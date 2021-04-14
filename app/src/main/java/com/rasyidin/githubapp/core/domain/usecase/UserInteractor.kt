package com.rasyidin.githubapp.core.domain.usecase

import android.content.Context
import android.database.Cursor
import com.rasyidin.githubapp.core.data.Resource
import com.rasyidin.githubapp.core.domain.model.User
import com.rasyidin.githubapp.core.domain.repository.IUserRepository
import kotlinx.coroutines.flow.Flow

class UserInteractor(private val userRepository: IUserRepository) : IUserUseCase {
    override fun getUsers(): Flow<Resource<List<User>>> {
        return userRepository.getUsers()
    }

    override fun getDetailUser(username: String?): Flow<Resource<User>> {
        return userRepository.getDetailUser(username)
    }

    override fun getUserFollowers(username: String?): Flow<Resource<List<User>>> {
        return userRepository.getUserFollowers(username)
    }

    override fun getUserFollowing(username: String?): Flow<Resource<List<User>>> {
        return userRepository.getUserFollowing(username)
    }

    override fun searchUsers(query: String?): Flow<Resource<List<User>>> {
        return userRepository.searchUsers(query)
    }

    override fun getFavoriteUsers(): Flow<List<User>> {
        return userRepository.getFavoriteUsers()
    }

    override suspend fun insertFavorite(user: User) {
        userRepository.insertFavorite(user)
    }

    override suspend fun deleteFavorite(user: User) {
        userRepository.deleteFavorite(user)
    }

    override suspend fun deleteFavoriteByUsername(username: String?) {
        userRepository.deleteFavoriteByUsername(username)
    }

    override fun getFavoriteCursor(): Cursor {
        return userRepository.getFavoriteCursor()
    }

    override fun setReminder(context: Context) {
        userRepository.setReminder(context)
    }

    override fun cancelReminder(context: Context) {
        userRepository.cancelReminder(context)
    }
}