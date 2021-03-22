package com.rasyidin.githubapp.core.domain.usecase

import com.rasyidin.githubapp.core.data.source.Resource
import com.rasyidin.githubapp.core.domain.model.User
import kotlinx.coroutines.flow.Flow

interface IUserUseCase {

    fun getUsers(): Flow<Resource<List<User>>>
}