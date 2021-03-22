package com.rasyidin.githubapp.core.data.source

sealed class Resource<T>(val data: T?, val message: String? = null) {
    class Success<T>(data: T?) : Resource<T>(data)
    class Loading<T>(data: T?): Resource<T>(data)
    class Error<T>(data: T? = null, message: String?): Resource<T>(data, message)

}