package com.rasyidin.githubapp.core.utils

import com.rasyidin.githubapp.core.data.source.local.entity.FavoriteEntity
import com.rasyidin.githubapp.core.data.source.remote.response.UserItemResponse
import com.rasyidin.githubapp.core.data.source.remote.response.UserRepositoryResponse
import com.rasyidin.githubapp.core.domain.model.Repository
import com.rasyidin.githubapp.core.domain.model.User

object Mapper {

    fun toUser(input: UserItemResponse): User {
        return input.let {
            User(
                id = it.id,
                avatar = it.avatarUrl,
                company = it.company,
                follower = it.follower,
                following = it.following,
                location = it.location,
                name = it.name,
                repository = it.repository,
                username = it.username,
                type = it.type,
                isFavorite = false
            )
        }
    }

    fun toFavoriteEntity(input: User): FavoriteEntity {
        return FavoriteEntity(
            id = input.id,
            avatarUrl = input.avatar,
            company = input.company,
            follower = input.follower,
            following = input.following,
            location = input.location,
            name = input.name,
            repository = input.repository,
            username = input.username ?: "",
            type = input.type
        )
    }

    fun toUser(input: FavoriteEntity?): User {
        return if (input != null) {
            User(
                id = input.id,
                avatar = input.avatarUrl,
                company = input.company,
                follower = input.follower,
                following = input.following,
                location = input.location,
                name = input.name,
                repository = input.repository,
                username = input.username,
                type = input.type,
                isFavorite = true
            )
        } else {
            User()
        }
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

@JvmName("entityToListUser")
fun List<FavoriteEntity>.toListUser(): List<User> {
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

fun List<UserRepositoryResponse>.toListRepository(): List<Repository> {
    val repositories = ArrayList<Repository>()
    this.map {
        val repository = Repository(
            name = it.name,
            reposUrl = it.reposUrl,
            description = it.description,
            starsCount = it.starsCount,
            watchersCount = it.watchersCount,
            forksCount = it.forksCount,
            language = it.language
        )
        repositories.add(repository)
    }
    return repositories
}