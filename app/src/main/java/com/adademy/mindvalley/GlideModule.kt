package com.adademy.mindvalley

import android.content.Context
import android.os.Build
import com.bumptech.glide.Glide
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader
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
}