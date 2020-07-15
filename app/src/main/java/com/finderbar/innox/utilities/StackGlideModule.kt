package com.finderbar.jovian.utilities.android

import android.content.Context
import android.graphics.drawable.Drawable
import android.graphics.drawable.PictureDrawable
import android.net.Uri
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.Options
import com.bumptech.glide.load.ResourceDecoder
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.Resource
import com.bumptech.glide.load.resource.SimpleResource
import com.bumptech.glide.load.resource.transcode.ResourceTranscoder
import com.bumptech.glide.module.AppGlideModule
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.finderbar.innox.R
import java.io.IOException
import java.io.InputStream


fun ImageView.loadLarge(uri: Uri, requestOptions: RequestOptions.() -> Unit = {}) {
    Glide.with(this)
        .load(uri)
        .thumbnail(0.25f)
        .apply(RequestOptions()
        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
        .skipMemoryCache(false)
        .apply(requestOptions))
        .into(this)
}

fun ImageView.loadAvatar(uri: Uri, requestOptions: RequestOptions.() -> Unit = {}) {
    Glide.with(this)
            .load(uri)
            .apply(RequestOptions.circleCropTransform())
            .priority(Priority.IMMEDIATE)
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            .skipMemoryCache(true)
            .priority(Priority.IMMEDIATE)
            .into(this)
}

