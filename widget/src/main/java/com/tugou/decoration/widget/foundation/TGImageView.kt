package com.tugou.decoration.widget.foundation

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import com.tugou.decoration.widget.R

class TGImageView @JvmOverloads constructor(
        context: Context,
        attr: AttributeSet? = null,
        defStyleAttr: Int = 0
) : AppCompatImageView(context, attr, defStyleAttr) {

    /** 宽高比, width/height = a.r. */
    private val aspectRatio: Float

    init {
        val a = context.obtainStyledAttributes(attr, R.styleable.TGImageView)
        aspectRatio = a.getFloat(R.styleable.TGImageView_tg_aspectRatio, 1f)
        a.recycle()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)

        when {
            widthMode == MeasureSpec.EXACTLY && heightMode != MeasureSpec.EXACTLY -> {
                val width = MeasureSpec.getSize(widthMeasureSpec)
                val height = (width / aspectRatio).toInt()
                super.onMeasure(
                        MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY),
                        MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY))
            }
            heightMode == MeasureSpec.EXACTLY && widthMode != MeasureSpec.EXACTLY -> {
                val height = MeasureSpec.getSize(heightMeasureSpec)
                val width = (aspectRatio * height).toInt()
                super.onMeasure(
                        MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY),
                        MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY))
            }
            else -> {
                super.onMeasure(widthMeasureSpec, heightMeasureSpec)
            }
        }
    }
}