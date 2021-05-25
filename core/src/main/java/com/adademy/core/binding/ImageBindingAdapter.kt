package com.adademy.core.binding

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions

object ImageViewBindingAdapter {
    @JvmStatic
    @BindingAdapter(value = ["binding:imageUrl", "binding:placeholder"], requireAll = true)
    fun loadImage(imageView: ImageView, url: String?, resource: Drawable) {
        if (!url.isNullOrBlank()) {
            Glide.with(imageView.context)
                .load(url)
                .placeholder(resource)
//                .override(imageView.width, imageView.height)
//                .apply(RequestOptions.centerCropTransform())
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .transition(DrawableTransitionOptions.withCrossFade(android.R.integer.config_mediumAnimTime))
                .into(imageView)
        } else {
            Glide.with(imageView.context)
                .load(resource)
                .into(imageView)
        }
    }

}