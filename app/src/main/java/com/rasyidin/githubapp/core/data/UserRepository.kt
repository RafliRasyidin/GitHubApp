package com.rasyidin.githubapp.core.data

import android.database.Cursor
import android.util.Log
import com.rasyidin.githubapp.core.data.source.local.LocalDataSource
import com.rasyidin.githubapp.core.data.source.remote.RemoteDataSource
import com.rasyidin.githubapp.core.data.source.remote.network.ApiResponse
import com.rasyidin.githubapp.core.data.source.remote.response.UserItemResponse
import com.rasyidin.githubapp.core.domain.model.User
import com.rasyidin.githubapp.core.domain.repository.IUserRepository
import com.rasyidin.githubapp.core.utils.Mapper
import com.rasyidin.githubapp.core.utils.toListUser
import kotlinx.coroutines.flow.*

@Suppress("UNCHECKED_CAST")
class UserRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : IUserRepository {

    companion object {
        const val TAG = "UserRepository"
    }

    override fun getUsers(): Flow<Resource<List<User>>> {
        return flow {
            emit(Resource.Loading(null))
            remoteDataSource.getUsers().collect { response ->
                when (response) {
                    is ApiResponse.Success -> {
                        emit(Resource.Success(response.data.toListUser()))
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

    override fun getDetailUser(username: String?): Flow<Resource<User>> {
        return object : NetworkBoundResource<User, UserItemResponse>() {
            override fun loadFromDb(): Flow<User> {
                return localDataSource.getDetailFavorite(username).map {
                    Mapper.toUser(it)
                }
            }

            override fun shouldFetch(data: User): Boolean {
                return data.username.isNullOrEmpty()
            }

            override suspend fun createCall(): Flow<ApiResponse<UserItemResponse>> {
                return remoteDataSource.getDetailUser(username)
            }

            override suspend fun callResponse(): Flow<Resource<User>> {
                return flow {
                    emit(Resource.Loading(null))
                    when (val response = remoteDataSource.getDetailUser(username).first()) {
                        is ApiResponse.Success -> {
                            emit(Resource.Success(Mapper.toUser(response.data)))
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
                } as Flow<Resource<User>>
            }
        }.asFlow()
    }

    override fun getUserFollowers(username: String?): Flow<Resource<List<User>>> {
        return flow {
            emit(Resource.Loading())
            remoteDataSource.getUserFollowers(username).collect { response ->
                when (response) {
                    is ApiResponse.Success -> {
                        emit(Resource.Success(response.data.toListUser()))
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

    override fun getUserFollowing(username: String?): Flow<Resource<List<User>>> {
        return flow {
            emit(Resource.Loading())
            remoteDataSource.getUserFollowing(username).collect { response ->
                when (response) {
                    is ApiResponse.Success -> {
                        emit(Resource.Success(response.data.toListUser()))
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

    override suspend fun searchUsers(query: String?): Resource<List<User>> {
        Resource.Loading(null)
        return when (val apiResponse = remoteDataSource.searchUsers(query).first()) {
            is ApiResponse.Empty -> {
                Resource.Error(null, apiResponse.toString())
            }
            is ApiResponse.Error -> Resource.Error(null, apiResponse.message)
            is ApiResponse.Success -> {
                val result = apiResponse.data.toListUser()
                Resource.Success(result)
            }
        }
    }

    override fun getFavoriteUsers(): Flow<List<User>> {
        return flow {
            emitAll(localDataSource.getFavoriteUsers().map {
                it.toListUser()
            })
        }
    }

    override suspend fun insertFavorite(user: User) {
        val userEntity = Mapper.toFavoriteEntity(user)
        localDataSource.insertFavorite(userEntity)
    }

    override suspend fun deleteFavorite(user: User) {
        val userEntity = Mapper.toFavoriteEntity(user)
        localDataSource.deleteFavorite(userEntity)
    }

    override suspend fun deleteFavoriteByUsername(username: String?) {
        localDataSource.deleteFavoriteByUsername(username)
    }

    override fun getFavoriteCursor(): Cursor {
        return localDataSource.getFavoriteCursor()
    }
}