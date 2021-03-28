package com.rasyidin.githubapp.core.data.source.remote

import android.util.Log
import com.rasyidin.githubapp.core.data.source.remote.network.ApiResponse
import com.rasyidin.githubapp.core.data.source.remote.network.ApiService
import com.rasyidin.githubapp.core.data.source.remote.response.UserItemResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RemoteDataSource(private val apiService: ApiService) {

    companion object {
        const val TAG = "RemoteDataSource"
    }

    suspend fun getUsers(): Flow<ApiResponse<List<UserItemResponse>>> {
        return flow {
            try {
                val response = apiService.getUsers()
                if (response.isNotEmpty()) {
                    emit(ApiResponse.Success(response))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.message.toString()))
                Log.e("RemoteDataSource", e.message.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getDetailUser(username: String?): Flow<ApiResponse<UserItemResponse>> {
        return flow {
            try {
                val response = apiService.getDetailUser(username)
                if (response.username?.isNotEmpty() ?: return@flow) {
                    emit(ApiResponse.Success(response))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.message.toString()))
                Log.e(TAG, e.message.toString())
            }
        }
    }

    suspend fun getUserFollowers(username: String?): Flow<ApiResponse<List<UserItemResponse>>> {
        return flow {
            try {
                val response = apiService.getUserFollowers(username)
                if (response.isNotEmpty()) {
                    emit(ApiResponse.Success(response))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.message.toString()))
                Log.e(TAG, e.message.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getUserFollowing(username: String?): Flow<ApiResponse<List<UserItemResponse>>> {
        return flow {
            try {
                val response = apiService.getUserFollowing(username)
                if (response.isNotEmpty()) {
                    emit(ApiResponse.Success(response))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.message.toString()))
                Log.e(TAG, e.message.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun searchUsers(query: String?): Flow<ApiResponse<List<UserItemResponse>>> {
        return flow {
            try {
                val response = apiService.searchUsers(query)
                val data = response.items
                if (data?.isNotEmpty() ?: return@flow) {
                    emit(ApiResponse.Success(data))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.message.toString()))
                Log.e(TAG, e.message.toString())
            }
        }.flowOn(Dispatchers.IO)
    }
}