package com.rasyidin.githubapp

import android.app.Application
import com.rasyidin.githubapp.core.di.databaseModule
import com.rasyidin.githubapp.core.di.networkModule
import com.rasyidin.githubapp.core.di.repositoryModule
import com.rasyidin.githubapp.di.useCaseModule
import com.rasyidin.githubapp.di.viewModelModule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

@FlowPreview
@ExperimentalCoroutinesApi
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@MyApplication)
            modules(
                listOf(
                    repositoryModule,
                    networkModule,
                    databaseModule,
                    useCaseModule,
                    viewModelModule
                )
            )
        }
    }
}