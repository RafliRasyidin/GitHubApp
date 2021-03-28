package com.rasyidin.githubapp.core.domain.usecase

import com.rasyidin.githubapp.core.data.source.Resource
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

    override suspend fun searchUsers(query: String?): Resource<List<User>> {
        return userRepository.searchUsers(query)
    }
}