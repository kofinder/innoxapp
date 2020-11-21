package com.finderbar.innox.utilities

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.PictureDrawable
import android.net.Uri
import android.widget.ImageView
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
import com.bumptech.glide.request.target.Target
import com.caverock.androidsvg.SVG
import com.caverock.androidsvg.SVGParseException
import com.finderbar.innox.R
import java.io.File
import java.io.IOException
import java.io.InputStream


@GlideModule
class StackGlideModule : AppGlideModule() {
    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
        registry.append(InputStream::class.java, SVG::class.java, SvgDecoder())
        registry.register(SVG::class.java, PictureDrawable::class.java, SvgDrawableTranscoder())
    }
}

class SvgDecoder : ResourceDecoder<InputStream, SVG> {
    override fun handles(source: InputStream, options: Options): Boolean {
        return true
    }

    @Throws(IOException::class)
    override fun decode(source: InputStream, width: Int, height: Int, options: Options): Resource<SVG> {
        try {
            val svg = SVG.getFromInputStream(source)
            return SimpleResource(svg)
        } catch (ex: SVGParseException) {
            throw IOException("Cannot load SVG from stream", ex)
        }
    }
}

class SvgDrawableTranscoder : ResourceTranscoder<SVG, PictureDrawable> {
    override fun transcode(toTranscode: Resource<SVG>, options: Options): Resource<PictureDrawable> {
        val svg = toTranscode.get()
        val picture = svg.renderToPicture()
        val drawable = PictureDrawable(picture)
        return SimpleResource(drawable)
    }
}


fun ImageView.loadLarge(uri: Uri, requestOptions: RequestOptions.() -> Unit = {}) {
    Glide.with(this)
        .load(uri)
        .thumbnail(0.25f)
        .apply(
            RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .skipMemoryCache(false)
                .apply(requestOptions)
        )
        .placeholder(R.drawable.placeholder)
        .error(R.drawable.placeholder)
        .into(this)
}

fun ImageView.loadAvatar(uri: Uri) {
    Glide.with(this)
        .load(uri)
        .apply(RequestOptions.circleCropTransform())
        .priority(Priority.IMMEDIATE)
        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
        .skipMemoryCache(true)
        .priority(Priority.IMMEDIATE)
        .placeholder(R.drawable.spinner)
        .error(R.drawable.spinner)
        .into(this)
}

fun ImageView.loadAvatarWithCache(uri: Uri) {
    Glide.with(this)
        .load(uri)
        .apply(RequestOptions.circleCropTransform())
        .diskCacheStrategy(DiskCacheStrategy.DATA)
        .placeholder(R.drawable.spinner)
        .error(R.drawable.spinner)
        .into(this)
}

fun ImageView.drawableImage(uri: Uri) {
    Glide.with(this)
        .load(uri)
        .apply(RequestOptions.circleCropTransform())
        .priority(Priority.IMMEDIATE)
        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
        .skipMemoryCache(true)
        .priority(Priority.IMMEDIATE)
        .placeholder(R.drawable.spinner)
        .error(R.drawable.spinner)
        .into(this)
}

fun convertUriToBitmap(context: Context, uri: Uri): File? = Glide.with(context)
    .downloadOnly()
    .diskCacheStrategy(DiskCacheStrategy.DATA)
    .load(uri)
    .placeholder(R.drawable.spinner)
    .submit(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).get()



