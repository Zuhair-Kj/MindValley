package com.adademy.mindvalley

import android.content.Context
import android.os.Build
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory
import com.bumptech.glide.load.engine.cache.LruResourceCache
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.module.AppGlideModule
import java.io.InputStream


@GlideModule
class UnsafeOkHttpGlideModule : AppGlideModule() {
    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) return
        val client = UnsafeOkHttpClient.getUnsafeOkHttpClient()

        registry.replace(GlideUrl::class.java, InputStream::class.java, OkHttpUrlLoader.Factory(client))
        glide.registry.replace(GlideUrl::class.java, InputStream::class.java,
                OkHttpUrlLoader.Factory(client))
    }

    override fun applyOptions(context: Context, builder: GlideBuilder) {
        builder.setMemoryCache(LruResourceCache(1024 * 1024 * 20))
        builder.setDiskCache(InternalCacheDiskCacheFactory(context, 1024 * 1024 * 100))
    }
}