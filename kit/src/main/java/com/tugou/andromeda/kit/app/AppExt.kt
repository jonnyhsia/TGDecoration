@file:JvmName("AppKit")

package com.tugou.andromeda.kit.app

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Environment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.io.File
import java.io.FileOutputStream
import java.util.*

/**
 * 是否是同一天
 */
fun Date.isSameDay(timeInMills: Long): Boolean {
    return this.time.isSameDay(timeInMills)
}

fun Long.isSameDay(timeInMills: Long): Boolean {
    val calendarA = Calendar.getInstance().apply {
        this.timeInMillis = this@isSameDay
    }
    val calendarB = Calendar.getInstance().apply {
        this.timeInMillis = timeInMills
    }

    return calendarA.get(Calendar.YEAR) == calendarB.get(Calendar.YEAR)
            && calendarA.get(Calendar.MONTH) == calendarB.get(Calendar.MONTH)
            && calendarA.get(Calendar.DAY_OF_MONTH) == calendarB.get(Calendar.DAY_OF_MONTH)
}

/**
 * App 是否安装
 */
fun Context.isAppInstalled(packageName: String): Boolean {
    val installedAppList = packageManager.getInstalledPackages(0)

    // 如果为 null 或 空则返回 false
    if (installedAppList?.isNotEmpty() != true) {
        return false
    }

    return installedAppList.any { it.packageName == packageName }
}

/**
 * 将图片保存到本地
 *
 * @param imageUrl 图片链接
 * @param filename 默认为 TG_时间
 * @param path     默认保存到下载路径
 */
@JvmOverloads
fun Context.saveImageToLocal(
        imageUrl: String,
        filename: String = "TG_${System.currentTimeMillis() / 1000}",
        path: String? = null
): Single<String> {
    val loadBitmap = Single.create<Bitmap> {
        // 加载图片, 获取 Bitmap 对象
        Glide.with(this).asBitmap().load(imageUrl)
                .into(object : SimpleTarget<Bitmap>() {
                    override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                        it.onSuccess(resource)
                    }

                    override fun onLoadFailed(errorDrawable: Drawable?) {
                        super.onLoadFailed(errorDrawable)
                        it.onError(IllegalStateException("图片加载失败"))
                    }
                })
    }

    return loadBitmap.subscribeOn(Schedulers.io())
            .flatMap { image ->
                Single.create<String> {
                    val file = File(path ?: "${Environment.getExternalStorageDirectory()}/Download")
                    if (!file.exists()) {
                        file.mkdirs()
                    }

                    val imageFile = File(file.absolutePath, "$filename.jpg")
                    val outStream = FileOutputStream(imageFile)
                    image.compress(Bitmap.CompressFormat.JPEG, 100, outStream)
                    outStream.flush()
                    outStream.close()

                    it.onSuccess(file.absolutePath)
                }
            }
            .observeOn(AndroidSchedulers.mainThread())
}