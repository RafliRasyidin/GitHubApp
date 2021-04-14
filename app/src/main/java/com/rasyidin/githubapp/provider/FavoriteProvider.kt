package com.rasyidin.githubapp.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.rasyidin.githubapp.core.domain.usecase.IUserUseCase
import com.rasyidin.githubapp.core.utils.Constants.AUTHORITY
import com.rasyidin.githubapp.core.utils.Constants.SCHEME
import com.rasyidin.githubapp.core.utils.Constants.TABLE_FAVORITE
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class FavoriteProvider : ContentProvider() {

    private val userUseCase: IUserUseCase by inject()

    private val contentUri: Uri = Uri.Builder().scheme(SCHEME)
        .authority(AUTHORITY)
        .appendPath(TABLE_FAVORITE)
        .build()

    companion object {
        private const val FAVORITE = 1
        private const val FAVORITE_ID = 2
        private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH)

        init {
            uriMatcher.addURI(AUTHORITY, TABLE_FAVORITE, FAVORITE)

            uriMatcher.addURI(AUTHORITY, "$TABLE_FAVORITE/#", FAVORITE_ID)
        }
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        GlobalScope.launch(Dispatchers.IO) {
            userUseCase.deleteFavoriteByUsername(uri.lastPathSegment.toString())
        }

        context?.contentResolver?.notifyChange(contentUri, null)
        return 1
    }

    override fun getType(uri: Uri): String? {
        return null
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        return null
    }

    override fun onCreate(): Boolean {
        return true
    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {
        return when (uriMatcher.match(uri)) {
            FAVORITE -> userUseCase.getFavoriteCursor()
            else -> null
        }
    }

    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        return 0
    }
}