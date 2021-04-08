package com.rasyidin.githubapp.core.utils

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.rasyidin.githubapp.core.utils.Constants.SUFFIX_CHARS
import java.math.RoundingMode
import java.text.DecimalFormat
import kotlin.math.ln
import kotlin.math.pow

fun ImageView.loadImage(url: String?, placeHolder: Int, errorImage: Int) {
    Glide.with(this.context)
        .load(url)
        .placeholder(placeHolder)
        .error(errorImage)
        .centerCrop()
        .into(this)
}

fun Int.toShortNumberDisplay(): String {
    val doubleNumber = this.toDouble()
    val formatter = DecimalFormat("###.#")
    val isNumberLessThanThousand = this < 1000.0
    formatter.roundingMode = RoundingMode.DOWN

    return if (isNumberLessThanThousand) {
        formatter.format(doubleNumber)
    } else {
        val exp = (ln(doubleNumber) / ln(1000.0)).toInt()
        formatter.format(this / 1000.0.pow(exp.toDouble())) + SUFFIX_CHARS[exp - 1]
    }
}