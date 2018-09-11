package com.tugou.decoration.widget.design

import android.content.Context
import android.util.AttributeSet
import android.widget.ScrollView
import android.widget.Scroller
import com.tugou.decoration.widget.R
import me.everything.android.ui.overscroll.VerticalOverScrollBounceEffectDecorator
import me.everything.android.ui.overscroll.adapters.ScrollViewOverScrollDecorAdapter

class SpeedScrollView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : ScrollView(context, attrs, defStyleAttr) {

    private val scroller = Scroller(context)
    private var scrollChangedListener: OnScrollChangedListener? = null
    private val bounceEffectDecorator: VerticalOverScrollBounceEffectDecorator
    private val speed: Float

    init {
        val a = context.obtainStyledAttributes(attrs, R.styleable.SpeedScrollView)
        speed = a.getFloat(R.styleable.SpeedScrollView_tg_speed, 0.8f)
        a.recycle()

        bounceEffectDecorator = VerticalOverScrollBounceEffectDecorator(ScrollViewOverScrollDecorAdapter(this), 2f, 1f, -2f)
    }

    //调用此方法滚动到目标位置  duration滚动时间
    fun smoothScrollToSlow(fx: Int, fy: Int, duration: Int) {
        val dx = fx - scrollX//scroller.getFinalX();  普通view使用这种方法
        val dy = fy - scrollY  //scroller.getFinalY();
        smoothScrollBySlow(dx, dy, duration)
    }

    //调用此方法设置滚动的相对偏移
    fun smoothScrollBySlow(dx: Int, dy: Int, duration: Int) {
        //设置mScroller的滚动偏移量
        scroller.startScroll(scrollX, scrollY, dx, dy, duration)//scrollView使用的方法（因为可以触摸拖动）
        invalidate() //这里必须调用invalidate()才能保证computeScroll()会被调用，否则不一定会刷新界面，看不到滚动效果
    }

    override fun computeScroll() {
        //先判断mScroller滚动是否完成
        if (scroller.computeScrollOffset()) {

            //这里调用View的scrollTo()完成实际的滚动
            scrollTo(scroller.currX, scroller.currY)

            //必须调用该方法，否则不一定能看到滚动效果
            postInvalidate()
        }
        super.computeScroll()
    }

    /**
     * 滑动事件，这是控制手指滑动的惯性速度
     */
    override fun fling(velocityY: Int) {
        super.fling((velocityY * speed).toInt())
    }

    override fun onScrollChanged(l: Int, t: Int, oldl: Int, oldt: Int) {
        super.onScrollChanged(l, t, oldl, oldt)
        scrollChangedListener?.onScrollChanged(l, t, oldl, oldt)
    }

    fun setScrollChangedListener(listener: OnScrollChangedListener) {
        scrollChangedListener = listener
    }


    interface OnScrollChangedListener {

        fun onScrollChanged(currX: Int, currY: Int, oldl: Int, oldt: Int)
    }
}