package com.rasyidin.consumerapp.utils

import android.database.Cursor
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.rasyidin.consumerapp.model.User

fun ImageView.loadImage(url: String?, placeHolder: Int, errorImage: Int) {
    Glide.with(this.context)
        .load(url)
        .placeholder(placeHolder)
        .error(errorImage)
        .centerCrop()
        .into(this)
}

fun cursorToArrayList(cursor: Cursor?): ArrayList<User> {
    val list = ArrayList<User>()
    cursor?.apply {
        while (moveToNext()) {
            val username = getString(getColumnIndexOrThrow("username"))
            val id: Int = getInt(getColumnIndexOrThrow("id"))
            val avatar: String = getString(getColumnIndexOrThrow("avatar"))
            val type: String = getString(getColumnIndexOrThrow("type"))
            list.add(
                User(
                    id = id,
                    username = username,
                    avatar = avatar,
                    type = type
                )
            )
        }
    }
    return list
}