package com.rasyidin.githubapp.core.data.source.local.room

import android.database.Cursor
import androidx.room.*
import com.rasyidin.githubapp.core.data.source.local.entity.FavoriteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Query("SELECT * FROM favorite")
    fun getFavoriteUsers(): Flow<List<FavoriteEntity>>

    @Query("SELECT * FROM favorite WHERE username = :username")
    fun getDetailFavorite(username: String?): Flow<FavoriteEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(favoriteEntity: FavoriteEntity)

    @Delete
    suspend fun deleteFavorite(favoriteEntity: FavoriteEntity)

    @Query("DELETE FROM favorite WHERE username = :username")
    suspend fun deleteFavoriteByUsername(username: String?)

    @Query("SELECT * FROM favorite")
    fun getFavoriteCursor(): Cursor
}