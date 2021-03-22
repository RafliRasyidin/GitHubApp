package com.rasyidin.githubapp.core.data.source

import android.util.Log
import com.rasyidin.githubapp.core.data.source.remote.RemoteDataSource
import com.rasyidin.githubapp.core.data.source.remote.network.ApiResponse
import com.rasyidin.githubapp.core.domain.model.User
import com.rasyidin.githubapp.core.domain.repository.IUserRepository
import com.rasyidin.githubapp.core.utils.Mapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow

class UserRepository(private val remoteDataSource: RemoteDataSource) : IUserRepository {
    @Suppress("UNCHECKED_CAST")
    override fun getUsers(): Flow<Resource<List<User>>> {
        return flow {
            remoteDataSource.getUsers().collect { response ->
                when (response) {
                    is ApiResponse.Success -> {
                        emit(Resource.Success(Mapper.usersToUser(response.data)))
                    }
                    is ApiResponse.Error -> {
                        emit(Resource.Error(null, response.message))
                    }
                    is ApiResponse.Empty -> Log.e("UserRepository", response.toString())
                }
            }
        } as Flow<Resource<List<User>>>
    }
}