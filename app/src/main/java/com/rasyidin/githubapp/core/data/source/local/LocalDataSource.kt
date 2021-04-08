package com.rasyidin.githubapp.core.data.source.local

import com.rasyidin.githubapp.core.data.source.local.entity.FavoriteEntity
import com.rasyidin.githubapp.core.data.source.local.room.UserDao

class LocalDataSource(private val userDao: UserDao) {

    fun getFavoriteUsers() = userDao.getFavoriteUsers()

    fun getDetailFavorite(username: String?) = userDao.getDetailFavorite(username)

    suspend fun insertFavorite(favoriteEntity: FavoriteEntity) {
        userDao.insertFavorite(favoriteEntity)
    }

    suspend fun deleteFavorite(favoriteEntity: FavoriteEntity) {
        userDao.deleteFavorite(favoriteEntity)
    }
}