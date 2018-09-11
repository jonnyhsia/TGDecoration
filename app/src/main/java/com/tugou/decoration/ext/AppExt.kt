package com.tugou.decoration.ext

import android.content.Context
import com.tugou.decoration.DecorApp

/**
 * 获取全局的 Application 对象
 */
val Any.application: Context
    get() = DecorApp.INSTANCE