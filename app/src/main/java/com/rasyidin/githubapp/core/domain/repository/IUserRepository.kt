package com.rasyidin.githubapp.core.domain.repository

import com.rasyidin.githubapp.core.data.source.Resource
import com.rasyidin.githubapp.core.domain.model.User
import kotlinx.coroutines.flow.Flow

interface IUserRepository {

    fun getUsers(): Flow<Resource<List<User>>>
}