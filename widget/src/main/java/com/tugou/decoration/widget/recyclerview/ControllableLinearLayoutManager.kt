package com.tugou.decoration.widget.recyclerview

import android.content.Context
import android.util.DisplayMetrics
import androidx.annotation.IntRange
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView

class ControllableLinearLayoutManager @JvmOverloads constructor(
        context: Context,
        @param:ScrollDuration @field:ScrollDuration
        private val mMillsPerInch: Int,
        @RecyclerView.Orientation orientation: Int = RecyclerView.VERTICAL,
        reverseLayout: Boolean = false
) : LinearLayoutManager(context, orientation, reverseLayout) {

    private var mScrollCallback: ScrollCallback? = null

    override fun smoothScrollToPosition(recyclerView: RecyclerView, state: RecyclerView.State?, position: Int) {
        val scroller = object : LinearSmoothScroller(recyclerView.context) {
            override fun calculateSpeedPerPixel(displayMetrics: DisplayMetrics): Float {
                if (mMillsPerInch <= 0) {
                    // mills per inch is 25
                    super.calculateSpeedPerPixel(displayMetrics)
                }
                return 1f * mMillsPerInch / displayMetrics.densityDpi
            }

            override fun onStop() {
                super.onStop()
                if (mScrollCallback != null) {
                    mScrollCallback!!.onFinished()
                }
            }
        }
        scroller.targetPosition = position
        startSmoothScroll(scroller)
    }

    fun setScrollCallback(scrollCallback: ScrollCallback) {
        mScrollCallback = scrollCallback
    }

    interface ScrollCallback {
        /**
         * Called on smooth scroll finished
         */
        fun onFinished()
    }

    @IntRange(from = 0)
    @Retention(AnnotationRetention.SOURCE)
    @Target(AnnotationTarget.FIELD, AnnotationTarget.VALUE_PARAMETER)
    private annotation class ScrollDuration
}