@file:JvmName("RoundCorner")

package com.tugou.decoration.widget.image

import android.graphics.*
import com.bumptech.glide.load.Key
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import java.security.MessageDigest

class RoundCorner constructor(
        private val leftTop: Float = 0f,
        private val rightTop: Float = 0f,
        private val rightBottom: Float = 0f,
        private val leftBottom: Float = 0f
) : BitmapTransformation() {

    constructor(radius: Float) : this(radius, radius, radius, radius)

    private val ID: String = "com.tugou.decoration.widget.image.RoundCorner$leftTop$rightTop$leftBottom$rightBottom"
    private val ID_BYTES = ID.toByteArray(Key.CHARSET)

    override fun transform(
            pool: BitmapPool, toTransform: Bitmap, outWidth: Int, outHeight: Int): Bitmap {
        val width = toTransform.width
        val height = toTransform.height
        val bitmap = pool.get(width, height, Bitmap.Config.ARGB_8888)
        bitmap.setHasAlpha(true)
        val canvas = Canvas(bitmap)
        val paint = Paint()
        paint.isAntiAlias = true
        paint.shader = BitmapShader(toTransform, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
        val rect = RectF(0f, 0f, width.toFloat(), height.toFloat())
        val radii = floatArrayOf(leftTop, leftTop, rightTop, rightTop, rightBottom, rightBottom, leftBottom, leftBottom)
        val path = Path()
        path.addRoundRect(rect, radii, Path.Direction.CW)
        canvas.drawPath(path, paint)
        return bitmap
    }

    override fun equals(other: Any?): Boolean {
        if (other is RoundCorner) {
            return leftTop == other.leftTop
                    && rightTop == other.rightTop
                    && leftBottom == other.leftBottom
                    && rightBottom == other.rightBottom
        }
        return false
    }

    override fun hashCode(): Int {
        return ID.hashCode() + leftTop.hashCode() + rightTop.hashCode() + leftBottom.hashCode() + rightBottom.hashCode()
    }

    override fun updateDiskCacheKey(messageDigest: MessageDigest) {
        messageDigest.update(ID_BYTES)
    }
}