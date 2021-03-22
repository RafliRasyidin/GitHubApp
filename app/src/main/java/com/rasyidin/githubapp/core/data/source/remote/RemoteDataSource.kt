package com.rasyidin.githubapp.core.data.source.remote

import android.util.Log
import com.rasyidin.githubapp.core.data.source.remote.network.ApiResponse
import com.rasyidin.githubapp.core.data.source.remote.response.Users
import com.rasyidin.githubapp.core.utils.JsonHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RemoteDataSource(private val jsonHelper: JsonHelper) {

    suspend fun getUsers(): Flow<ApiResponse<List<Users>>> {
        return flow {
            try {
                val response = jsonHelper.getUsers()
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
}