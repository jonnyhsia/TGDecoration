@file:JvmName("UIKit")

package com.tugou.andromeda.kit.ui

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.Window
import android.view.animation.AnimationUtils
import android.view.animation.Interpolator
import androidx.annotation.ColorInt
import androidx.core.graphics.drawable.DrawableCompat

/**
 * 给 Drawable 设置颜色
 */
fun Drawable.makeTint(@ColorInt color: Int): Drawable {
    // mutate 操作以防修改应用中所有复用的 drawable
    val mutatedDrawable = DrawableCompat.wrap(this).mutate()
    DrawableCompat.setTint(mutatedDrawable, color)
    return mutatedDrawable
}

private var INTERPOLATOR_FAST_OUT_SLOW_IN: Interpolator? = null

/**
 * 获取快出缓入动画差值器
 */
fun Context.getFastOutSlowInInterpolator(): Interpolator {
    if (INTERPOLATOR_FAST_OUT_SLOW_IN == null) {
        INTERPOLATOR_FAST_OUT_SLOW_IN = AnimationUtils.loadInterpolator(
                this, android.R.interpolator.fast_out_slow_in)
    }
    return INTERPOLATOR_FAST_OUT_SLOW_IN!!
}

/**
 * 使 Window 的 content 绘制到 Notch 下
 *
 * @see <a href="https://dev.mi.com/console/doc/detail?pId=1293"/>
 */
fun Window.enableDrawNotch() {
    val flag = 0x00000100 or 0x00000200
    try {
        val method = Window::class.java.getMethod("addExtraFlags",
                Int::class.javaPrimitiveType)
        method.invoke(this, flag)
    } catch (e: Exception) {
        Log.i("Notch", "addExtraFlags not found.")
    }
}

/**
 * 获取设备是否有缺口
 */
val Context.deviceHaveNotch: Boolean
    get() {
        // TODO: 18/6/28 Jianhao Android P 原生对 Notch 的判断
        var result = false
        try {
            val cl = applicationContext.classLoader
            val systemPro = cl.loadClass("android.os.SystemProperties")
            val get = systemPro.getMethod("get", String::class.java)
            // miui only
            val obj = get.invoke(systemPro, "ro.miui.notch")
            if (obj != null) {
                result = obj == "1"
            }
        } catch (e: ClassNotFoundException) {
        } catch (e: NoSuchMethodException) {
        } catch (e: Exception) {
        }

        return result
    }