package com.adademy.core.binding

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.signature.ObjectKey

object ImageViewBindingAdapter {
    @JvmStatic
    @BindingAdapter(value = ["binding:imageUrl", "binding:placeholder"], requireAll = true)
    fun loadImage(imageView: ImageView, url: String?, resource: Drawable) {
        if (!url.isNullOrBlank()) {
            Glide.with(imageView.context)
                .load(url)
                .override(imageView.width, imageView.height)
                .placeholder(resource)
                .skipMemoryCache(false)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .into(imageView)
        } else {
            Glide.with(imageView.context)
                .load(resource)
                .into(imageView)
        }
    }

}