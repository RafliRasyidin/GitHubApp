package com.rasyidin.githubapp.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.rasyidin.githubapp.core.data.source.Resource
import com.rasyidin.githubapp.core.domain.model.User
import com.rasyidin.githubapp.core.domain.usecase.IUserUseCase

class MainViewModel(private val userUseCase: IUserUseCase) : ViewModel() {

    fun getUsers(): LiveData<Resource<List<User>>> {
        return userUseCase.getUsers().asLiveData()
    }

}