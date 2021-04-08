package com.rasyidin.githubapp.core.data

import com.rasyidin.githubapp.core.data.source.remote.network.ApiResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

abstract class NetworkBoundResource<ResultType, RequestType> {

    private val result: Flow<Resource<ResultType>> =
        flow {
            emit(Resource.Loading())
            val dbSource = loadFromDb().first()
            if (shouldFetch(dbSource)) {
                emit(Resource.Loading())
                emitAll(callResponse())
            } else {
                emitAll(loadFromDb().map { Resource.Success(it) })
            }
        }.flowOn(Dispatchers.IO)

    protected open fun onFetchFailed() {}

    protected abstract fun loadFromDb(): Flow<ResultType>

    protected abstract fun shouldFetch(data: ResultType): Boolean

    protected abstract suspend fun createCall(): Flow<ApiResponse<RequestType>>

    protected abstract suspend fun callResponse(): Flow<Resource<ResultType>>

    fun asFlow(): Flow<Resource<ResultType>> = result
}