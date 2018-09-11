@file:JvmName("FormatKit")

package com.tugou.andromeda.kit.format

import android.net.Uri
import android.util.Log
import androidx.collection.ArrayMap
import com.tugou.andromeda.kit.TGKit
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

/**
 * 将毫秒数转换成日期的字符串
 */
@JvmOverloads
fun Long.toDateText(pattern: String = "yyyy-MM-dd"): String {
    val format = SimpleDateFormat(pattern, Locale.CHINA)
    return format.format(Date(this))
}

const val MONEY_UNIT_NONE = 0
const val MONEY_UNIT_SYMBOL = 1
const val MONEY_UNIT_TEXT = 2

/**
 * 将小数格式化成字符串，会去掉小数后面无用的 0
 */
fun Float.trimNumberText(fl: Float): String {
    return NumberFormat.getInstance().format(fl).replace(",", "")
}

/**
 * 将 Float 格式化为人民币
 */
@JvmOverloads
fun Float.toMoneyText(unit: Int = MONEY_UNIT_NONE): String {
    return trimNumberText(this).toMoneyText(unit)
}

/**
 * 将 String 格式化为人民币
 */
fun String.toMoneyText(unit: Int = MONEY_UNIT_NONE): String {
    return when (unit) {
        MONEY_UNIT_NONE -> this
        MONEY_UNIT_SYMBOL -> "¥$this"
        MONEY_UNIT_TEXT -> "${this}元"
        else -> throw UnsupportedOperationException("非 Kit 中定义的 MONEY_UNIT_* 常量")
    }
}

fun Int.formatNumberUnit(): String {
    return if (this > 10000) {
        String.format("%.1f万", this * 1.0 / 10000)
    } else {
        this.toString() + ""
    }
}

fun String.formatNumberUnit(): String {
    var number = 0
    try {
        number = this.toInt()
    } catch (e: Exception) {
        e.printStackTrace()
        Log.e("TEST", e.message)
    }

    return if (number > 10000) {
        String.format("%.1f万", number * 1.0 / 10000)
    } else {
        this
    }
}

/**
 * 根据 [TGKit.injectStaticAppUrlParameters] 注入的 Params 为 Url 添加 App 参数
 */
fun String.toAppUrl(): String {
    val uri = Uri.parse(this)
    if (!uri.isHierarchical) {
        return this
    }

    val uriBuilder = uri.buildUpon()
    for ((key, value) in TGKit.INJECTED_APP_PARAMS.entries) {
        if (!uri.containQueryParameter(key)) {
            uriBuilder.appendQueryParameter(key, value)
        }
    }

    return uriBuilder.toString()
}


/**
 * 在 AppUrl 的基础上添加用户相关的参数
 */
fun String.toAppUrlWithUserInfo(): String {
    val uri = Uri.parse(this)
    if (!uri.isHierarchical) {
        return this
    }

    val uriBuilder = uri.buildUpon()
    val params = ArrayMap<String, String>()
    params.putAll(from = TGKit.INJECTED_APP_PARAMS)
    params.putAll(TGKit.INJECTED_USER_PARAMS())

    for ((key, value) in params.entries) {
        if (!uri.containQueryParameter(key)) {
            uriBuilder.appendQueryParameter(key, value)
        }
    }

    return uriBuilder.toString()
}

private const val URL_IMG_1 = "https://pic.tugou.com"
private const val URL_IMG_2 = "https://pic.tugou.com/"

/**
 * 尝试转换成正确的 Image Url
 */
fun String.toTGImageUrl(): String {
    val builder = StringBuilder()
    when {
        startsWith("http") -> builder.append(this)
        startsWith("//pic.tugou.com/") -> builder.append("https:").append(this)
        startsWith("//static.tugou.com/") -> builder.append("https:").append(this)
        startsWith("/") -> builder.append(URL_IMG_1).append(this)
        else -> builder.append(URL_IMG_2).append(this)
    }
    return builder.toString()
}

/**
 * 将数组连接成字符串
 */
fun Collection<String>.arrayJoinToString(separator: String) = joinToString(separator)

private fun Uri.containQueryParameter(key: String): Boolean = !getQueryParameter(key).isNullOrEmpty()