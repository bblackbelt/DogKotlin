package com.blackbelt.dogkotlin.utils

import android.databinding.BindingAdapter
import android.graphics.drawable.BitmapDrawable
import android.support.v7.graphics.Palette
import android.text.TextUtils
import android.widget.ImageView
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import com.squareup.picasso.RequestCreator


fun ImageView.generatePalette(listener: (Palette) -> Unit) {
    Palette.from((this.drawable as BitmapDrawable).bitmap).generate(listener)
}

@BindingAdapter("imageUrl")
fun ImageView.loadUrl(url: String?) {
    if (TextUtils.isEmpty(url)) {
        return
    }
    Picasso.with(this.context).load(url).into(this)
}

inline fun ImageView.loadUrl(url: String, callback: PicassoCallback.() -> Unit) {
    Picasso.with(this.context).load(url).intoWithCallback(this, callback)
}

inline fun RequestCreator.intoWithCallback(target: ImageView, callback: PicassoCallback.() -> Unit) {
    this.into(target, PicassoCallback().apply(callback))
}

class PicassoCallback : Callback {

    private var onSuccess: (() -> Unit)? = null
    private var onError: (() -> Unit)? = null

    override fun onSuccess() {
        onSuccess?.invoke()
    }

    override fun onError() {
        onError?.invoke()
    }

    fun onSuccess(function: () -> Unit) {
        this.onSuccess = function
    }

    fun onError(function: () -> Unit) {
        this.onError = function
    }
}