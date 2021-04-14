package com.rasyidin.githubapp.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.rasyidin.githubapp.core.data.Resource
import com.rasyidin.githubapp.core.domain.model.User
import com.rasyidin.githubapp.core.domain.usecase.IUserUseCase
import com.rasyidin.githubapp.core.utils.Constants.DEBOUNCE_DURATION
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*

@FlowPreview
@ExperimentalCoroutinesApi
class MainViewModel(private val userUseCase: IUserUseCase) : ViewModel() {

    val getUsers: LiveData<Resource<List<User>>> =
        userUseCase.getUsers().asLiveData(Dispatchers.IO)

    val queryChannel = BroadcastChannel<String>(Channel.CONFLATED)

    val searchUsers = queryChannel.asFlow()
        .debounce(DEBOUNCE_DURATION)
        .distinctUntilChanged()
        .filter {
            it.trim().isNotEmpty()
        }
        .mapLatest { query ->
            userUseCase.searchUsers(query).asLiveData()
        }
        .asLiveData(viewModelScope.coroutineContext)

}