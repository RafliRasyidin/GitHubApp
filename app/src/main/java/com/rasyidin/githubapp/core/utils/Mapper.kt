package com.rasyidin.githubapp.core.utils

import com.rasyidin.githubapp.core.data.source.remote.response.UserItemResponse
import com.rasyidin.githubapp.core.domain.model.User

object Mapper {

    fun toUser(input: UserItemResponse): User {
        return User(
            id = input.id,
            avatar = input.avatarUrl,
            company = input.company,
            follower = input.follower,
            following = input.following,
            location = input.location,
            name = input.name,
            repository = input.repository,
            username = input.username,
            type = input.type
        )
    }

}

fun List<UserItemResponse>.toListUser(): List<User> {
    val users = ArrayList<User>()
    this.map {
        val user = User(
            id = it.id,
            avatar = it.avatarUrl,
            company = it.company,
            follower = it.follower,
            following = it.following,
            location = it.location,
            name = it.name,
            repository = it.repository,
            username = it.username,
            type = it.type
        )
        users.add(user)
    }
    return users
}