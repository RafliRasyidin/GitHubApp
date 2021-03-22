package com.rasyidin.githubapp.di

import com.rasyidin.githubapp.core.domain.usecase.IUserUseCase
import com.rasyidin.githubapp.core.domain.usecase.UserInteractor
import com.rasyidin.githubapp.ui.main.MainViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    factory<IUserUseCase> { UserInteractor(get()) }
}

val viewModelModule = module {
    viewModel { MainViewModel(get()) }
}