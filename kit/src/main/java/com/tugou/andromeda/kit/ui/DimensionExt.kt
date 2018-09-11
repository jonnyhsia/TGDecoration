@file:JvmName("DimensionKit")

package com.tugou.andromeda.kit.ui

import android.content.Context
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.WindowManager

/**
 * dp 转 px
 */
fun Context.dp2px(dpValue: Float) =
        TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, resources.displayMetrics)

/**
 * sp 转 px
 */
fun Context.sp2px(spValue: Float): Float =
        TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spValue, resources.displayMetrics)

/**
 * px 转 sp
 */
fun Context.px2sp(pxValue: Float): Float =
        pxValue / resources.displayMetrics.scaledDensity


/**
 * 获取状态栏高度
 */
val Context.statusBarHeight: Int
    get() {
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android").takeIf { it > 0 }
        return resourceId?.let(resources::getDimensionPixelSize) ?: 0
    }

val Context.displaySize: Pair<Int, Int>
    get() {
        val wm = getSystemService(Context.WINDOW_SERVICE) as WindowManager?
                ?: return Pair(0, 0)
        val outMetrics = DisplayMetrics()
        wm.defaultDisplay.getMetrics(outMetrics)
        with(outMetrics) {
            return Pair(widthPixels, heightPixels)
        }
    }

/**
 * 获取屏幕宽度
 */
val Context.displayHeight: Int
    get() {
        val wm = getSystemService(Context.WINDOW_SERVICE) as WindowManager?
                ?: return 0
        val outMetrics = DisplayMetrics()
        wm.defaultDisplay.getMetrics(outMetrics)
        return outMetrics.heightPixels
    }

/**
 * 获取屏幕宽度
 */
val Context.displayWidth: Int
    get() {
        val wm = getSystemService(Context.WINDOW_SERVICE) as WindowManager?
                ?: return 0
        val outMetrics = DisplayMetrics()
        wm.defaultDisplay.getMetrics(outMetrics)
        return outMetrics.widthPixels
    }