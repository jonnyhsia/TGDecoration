package com.tugou.decoration.widget.foundation

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.annotation.IntDef
import androidx.viewpager.widget.ViewPager
import com.tugou.decoration.widget.R
import me.everything.android.ui.overscroll.HorizontalOverScrollBounceEffectDecorator
import me.everything.android.ui.overscroll.adapters.ViewPagerOverScrollDecorAdapter

class AutoSizeViewPager @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null
) : ViewPager(context, attrs) {

    @Mode
    private var mMeasureMode: Int = 0

    init {
        HorizontalOverScrollBounceEffectDecorator(ViewPagerOverScrollDecorAdapter(this), 2f, 1f, -2f)

        if (attrs == null) {
            mMeasureMode = MODE_FAST
        } else {
            val typedArray = context.obtainStyledAttributes(attrs, R.styleable.AutoSizeViewPager)
            mMeasureMode = typedArray.getInt(R.styleable.AutoSizeViewPager_measureMode, MODE_FAST)
            typedArray.recycle()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        if (MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.EXACTLY) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec)
            return
        }

        var height = 0
        val count = childCount

        // 下面遍历所有child的高度
        for (i in 0 until count) {
            val child = getChildAt(i)
            child.measure(widthMeasureSpec,
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED))
            val h = child.measuredHeight
            //采用最大的view的高度。
            if (h > height) {
                height = h
            }
            if (mMeasureMode == MODE_FAST) {
                break
            }
        }
        val actualHeightMeasureSpec = View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.EXACTLY)
        super.onMeasure(widthMeasureSpec, actualHeightMeasureSpec)
    }

    /**
     * Mode 取值注解
     */
    @IntDef(MODE_FAST, MODE_ACCURATE)
    @Retention(AnnotationRetention.SOURCE)
    internal annotation class Mode

    companion object {
        const val MODE_FAST = 1
        const val MODE_ACCURATE = 2
    }
}