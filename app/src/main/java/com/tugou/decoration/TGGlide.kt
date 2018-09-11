package com.tugou.decoration

import com.bumptech.glide.annotation.GlideExtension
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.annotation.GlideOption
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.module.AppGlideModule
import com.bumptech.glide.request.RequestOptions
import com.tugou.andromeda.kit.ui.dp2px

@GlideModule
class TGGlideModule : AppGlideModule()

@GlideExtension
object TGGlideExtension {

    @JvmStatic
    @GlideOption
    fun simpleOption(options: RequestOptions): RequestOptions {
        return options.diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.placeholder_rounded_gray)
                .error(R.drawable.placeholder_rounded_gray)
    }

    @JvmStatic
    @GlideOption
    fun commonOption(options: RequestOptions): RequestOptions {
        return options.transforms(CenterCrop(), RoundedCorners(DecorApp.INSTANCE.dp2px(4F).toInt()))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.placeholder_rounded_gray)
                .error(R.drawable.placeholder_rounded_gray)
    }

    @JvmStatic
    @GlideOption
    fun asAvatar(options: RequestOptions): RequestOptions {
        return options.transforms(CenterCrop(), RoundedCorners(DecorApp.INSTANCE.dp2px(4F).toInt()))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.placeholder_rounded_gray)
                .error(R.drawable.placeholder_rounded_gray)
    }
}