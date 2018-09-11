package com.tugou.core

import android.graphics.Color

open class StatusConfig(
        val statusBarColor: Int = Color.WHITE,
        val isLight: Boolean = true,
        val isTranslucent: Boolean = false,
        val isWindowFullscreen: Boolean = false,
        val isLayoutFullscreen: Boolean = false
)

// object DefaultStatusConfig : StatusConfig()