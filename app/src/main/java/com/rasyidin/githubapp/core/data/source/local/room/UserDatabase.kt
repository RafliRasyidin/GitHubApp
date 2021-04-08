package com.rasyidin.githubapp.core.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rasyidin.githubapp.core.data.source.local.entity.FavoriteEntity

@Database(
    entities = [FavoriteEntity::class],
    version = 1,
    exportSchema = false
)
abstract class UserDatabase : RoomDatabase() {
    abstract fun getUserDao(): UserDao
}