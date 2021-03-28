package com.rasyidin.githubapp.core.data.source

import android.util.Log
import com.rasyidin.githubapp.core.data.source.remote.RemoteDataSource
import com.rasyidin.githubapp.core.data.source.remote.network.ApiResponse
import com.rasyidin.githubapp.core.domain.model.User
import com.rasyidin.githubapp.core.domain.repository.IUserRepository
import com.rasyidin.githubapp.core.utils.Mapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

class UserRepository(private val remoteDataSource: RemoteDataSource) : IUserRepository {

    companion object {
        const val TAG = "UserRepository"
    }

    @Suppress("UNCHECKED_CAST")
    override fun getUsers(): Flow<Resource<List<User>>> {
        return flow {
            emit(Resource.Loading(null))
            remoteDataSource.getUsers().collect { response ->
                when (response) {
                    is ApiResponse.Success -> {
                        emit(Resource.Success(Mapper.listUserItemsResponseToListUser(response.data)))
                    }
                    is ApiResponse.Error -> {
                        emit(Resource.Error(null, response.message))
                    }
                    is ApiResponse.Empty -> {
                        emit(Resource.Error(null, response.toString()))
                        Log.e(TAG, response.toString())
                    }
                }
            }
        } as Flow<Resource<List<User>>>
    }

    @Suppress("UNCHECKED_CAST")
    override fun getDetailUser(username: String?): Flow<Resource<User>> {
        return flow {
            emit(Resource.Loading(null))
            remoteDataSource.getDetailUser(username).collect { response ->
                when (response) {
                    is ApiResponse.Success -> {
                        emit(Resource.Success(Mapper.userItemsResponseToUser(response.data)))
                    }
                    is ApiResponse.Empty -> {
                        emit(Resource.Error(null, response.toString()))
                        Log.e(TAG, response.toString())
                    }
                    is ApiResponse.Error -> {
                        emit(Resource.Error(null, response.message))
                        Log.e(TAG, response.toString())
                    }
                }
            }
        } as Flow<Resource<User>>
    }

    @Suppress("UNCHECKED_CAST")
    override fun getUserFollowers(username: String?): Flow<Resource<List<User>>> {
        return flow {
            emit(Resource.Loading(null))
            remoteDataSource.getUserFollowers(username).collect { response ->
                when (response) {
                    is ApiResponse.Success -> {
                        emit(Resource.Success(Mapper.listUserItemsResponseToListUser(response.data)))
                    }
                    is ApiResponse.Empty -> {
                        emit(Resource.Error(null, response.toString()))
                        Log.e(TAG, response.toString())
                    }
                    is ApiResponse.Error -> {
                        emit(Resource.Error(null, response.message))
                    }
                }
            }
        } as Flow<Resource<List<User>>>
    }

    @Suppress("UNCHECKED_CAST")
    override fun getUserFollowing(username: String?): Flow<Resource<List<User>>> {
        return flow {
            emit(Resource.Loading(null))
            remoteDataSource.getUserFollowing(username).collect { response ->
                when (response) {
                    is ApiResponse.Success -> {
                        emit(Resource.Success(Mapper.listUserItemsResponseToListUser(response.data)))
                    }
                    is ApiResponse.Empty -> {
                        emit(Resource.Error(null, response.toString()))
                        Log.e(TAG, response.toString())
                    }
                    is ApiResponse.Error -> {
                        emit(Resource.Error(null, response.message))
                    }
                }
            }
        } as Flow<Resource<List<User>>>
    }

    /*@Suppress("UNCHECKED_CAST")*/
    override suspend fun searchUsers(query: String?): Resource<List<User>> {
        /*return flow {
            emit(Resource.Loading(null))
            remoteDataSource.searchUsers(query).collect { response ->
                when (response) {
                    ApiResponse.Empty -> {
                        emit(Resource.Error(null, response.toString()))
                        Log.e(TAG, response.toString())
                    }
                    is ApiResponse.Error -> {
                        emit(Resource.Error(null, response.message))
                        Log.e(TAG, response.message)
                    }
                    is ApiResponse.Success -> {
                        emit(Resource.Success(Mapper.listUserItemsResponseToListUser(response.data)))
                    }
                }
            }
        } as Flow<Resource<List<User>>>*/
        return when (val apiResponse = remoteDataSource.searchUsers(query).first()) {
            is ApiResponse.Empty -> {
                Resource.Error(null, apiResponse.toString())
            }
            is ApiResponse.Error -> Resource.Error(null, apiResponse.message)
            is ApiResponse.Success -> {
                val result = Mapper.listUserItemsResponseToListUser(apiResponse.data)
                Resource.Success(result)
            }
        }
    }
}