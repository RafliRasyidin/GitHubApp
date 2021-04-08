package com.rasyidin.githubapp.core.data.source.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "favorite")
data class FavoriteEntity(
    @PrimaryKey
    @ColumnInfo(name = "username")
    val username: String,

    @ColumnInfo(name = "id")
    val id: Int = 0,

    @ColumnInfo(name = "avatar")
    val avatarUrl: String? = "",

    @ColumnInfo(name = "company")
    val company: String? = "",

    @ColumnInfo(name = "followers")
    val follower: Int = 0,

    @ColumnInfo(name = "following")
    val following: Int = 0,

    @ColumnInfo(name = "location")
    val location: String? = "",

    @ColumnInfo(name = "name")
    val name: String? = "",

    @ColumnInfo(name = "repository")
    val repository: Int = 0,

    @ColumnInfo(name = "type")
    val type: String? = ""
) : Parcelable