package com.rasyidin.githubapp.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.rasyidin.githubapp.core.data.source.Resource
import com.rasyidin.githubapp.core.domain.model.User
import com.rasyidin.githubapp.core.domain.usecase.IUserUseCase

class DetailViewModel(private val userUseCase: IUserUseCase) : ViewModel() {

    fun getDetailUser(username: String?): LiveData<Resource<User>> {
        return userUseCase.getDetailUser(username).asLiveData()
    }

    fun getUserFollowers(username: String?): LiveData<Resource<List<User>>> {
        return userUseCase.getUserFollowers(username).asLiveData()
    }

    fun getUserFollowing(username: String?): LiveData<Resource<List<User>>> {
        return userUseCase.getUserFollowing(username).asLiveData()
    }
}