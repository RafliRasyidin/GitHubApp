package com.rasyidin.githubapp.core.utils

import android.widget.ImageView
import com.bumptech.glide.Glide

fun ImageView.loadImage(url: String?, placeHolder: Int, errorImage: Int) {
    Glide.with(this.context)
        .load(url)
        .placeholder(placeHolder)
        .error(errorImage)
        .centerCrop()
        .into(this)
}