package com.rasyidin.githubapp.core.di

import com.rasyidin.githubapp.core.data.source.UserRepository
import com.rasyidin.githubapp.core.data.source.remote.RemoteDataSource
import com.rasyidin.githubapp.core.domain.repository.IUserRepository
import com.rasyidin.githubapp.core.utils.JsonHelper
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val repositoryModule = module {
    single { JsonHelper(androidContext()) }
    single { RemoteDataSource(get()) }
    single<IUserRepository> { UserRepository(get()) }
}