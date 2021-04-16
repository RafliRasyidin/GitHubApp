package com.rasyidin.githubapp.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rasyidin.githubapp.core.data.Resource
import com.rasyidin.githubapp.core.domain.model.Repository
import com.rasyidin.githubapp.core.domain.model.User
import com.rasyidin.githubapp.core.domain.usecase.IUserUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class DetailViewModel(private val userUseCase: IUserUseCase) : ViewModel() {

    private var _detailUser = MutableLiveData<Resource<User>>()
    val detailUser: LiveData<Resource<User>> = _detailUser

    private var _followersUser = MutableLiveData<Resource<List<User>>>()
    val followersUser: LiveData<Resource<List<User>>> = _followersUser

    private var _followingUser = MutableLiveData<Resource<List<User>>>()
    val followingUser: LiveData<Resource<List<User>>> = _followingUser

    private var _repositoryUser = MutableLiveData<Resource<List<Repository>>>()
    val repositoryUser: LiveData<Resource<List<Repository>>> = _repositoryUser

    fun getDetailUser(username: String?) =
        viewModelScope.launch {
            userUseCase.getDetailUser(username).collect { resource ->
                _detailUser.postValue(resource)
            }
        }

    fun getUserFollowers(username: String?) =
        viewModelScope.launch {
            userUseCase.getUserFollowers(username).collect { resource ->
                _followersUser.postValue(resource)
            }
        }

    fun getUserFollowing(username: String?) =
        viewModelScope.launch {
            userUseCase.getUserFollowing(username).collect { resource ->
                _followingUser.postValue(resource)
            }
        }

    fun getUserRepository(username: String?) =
        viewModelScope.launch {
            userUseCase.getUserRepository(username).collect { resource ->
                _repositoryUser.postValue(resource)
            }
        }

    fun insertFavorite(user: User) =
        viewModelScope.launch(Dispatchers.IO) {
            userUseCase.insertFavorite(user)
        }

    fun deleteFavorite(user: User) =
        viewModelScope.launch(Dispatchers.IO) {
            userUseCase.deleteFavorite(user)
        }

}