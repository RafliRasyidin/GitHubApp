package com.rasyidin.githubapp.di

import com.rasyidin.githubapp.core.domain.usecase.IUserUseCase
import com.rasyidin.githubapp.core.domain.usecase.UserInteractor
import com.rasyidin.githubapp.ui.detail.DetailViewModel
import com.rasyidin.githubapp.ui.favorite.FavoriteViewModel
import com.rasyidin.githubapp.ui.main.MainViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    factory<IUserUseCase> { UserInteractor(get()) }
}

@ExperimentalCoroutinesApi
@FlowPreview
val viewModelModule = module {
    viewModel { MainViewModel(get()) }
    viewModel { DetailViewModel(get()) }
    viewModel { FavoriteViewModel(get()) }
}