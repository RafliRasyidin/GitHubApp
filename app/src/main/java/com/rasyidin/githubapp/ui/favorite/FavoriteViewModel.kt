package com.rasyidin.githubapp.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.rasyidin.githubapp.core.domain.model.User
import com.rasyidin.githubapp.core.domain.usecase.IUserUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoriteViewModel(private val userUseCase: IUserUseCase) : ViewModel() {

    val getFavorite: LiveData<List<User>> =
        userUseCase.getFavoriteUsers().asLiveData(Dispatchers.IO)

    fun deleteUser(user: User) =
        viewModelScope.launch(Dispatchers.IO) {
            userUseCase.deleteFavorite(user)
        }

    fun insertFavorite(user: User) =
        viewModelScope.launch(Dispatchers.IO) {
            userUseCase.insertFavorite(user)
        }

}