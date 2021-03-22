package com.rasyidin.githubapp.core.utils

import com.rasyidin.githubapp.core.data.source.remote.response.Users
import com.rasyidin.githubapp.core.domain.model.User

object Mapper {

    fun usersToUser(input: List<Users>): List<User> {
        val users = ArrayList<User>()
        input.map {
            val user = User(
                avatar = it.avatar,
                company = it.company,
                follower = it.follower,
                following = it.following,
                location = it.location,
                name = it.name,
                repository = it.repository,
                username = it.username
            )
            users.add(user)
        }
        return users
    }
}