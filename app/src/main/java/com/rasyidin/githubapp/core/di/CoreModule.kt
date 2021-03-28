package com.rasyidin.githubapp.core.di

import com.rasyidin.githubapp.core.data.source.UserRepository
import com.rasyidin.githubapp.core.data.source.remote.RemoteDataSource
import com.rasyidin.githubapp.core.data.source.remote.network.ApiService
import com.rasyidin.githubapp.core.domain.repository.IUserRepository
import com.rasyidin.githubapp.core.utils.Constants.BASE_URL
import com.rasyidin.githubapp.core.utils.Constants.REQUEST_TIMEOUT
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val repositoryModule = module {
    single { RemoteDataSource(get()) }
    single<IUserRepository> { UserRepository(get()) }
}

val networkModule = module {
    single {
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
            .build()
    }

    single {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
        retrofit.create(ApiService::class.java)
    }
}