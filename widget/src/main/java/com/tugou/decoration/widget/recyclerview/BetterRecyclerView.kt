package com.tugou.decoration.widget.recyclerview

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ViewConfiguration
import androidx.recyclerview.widget.RecyclerView

private const val INVALID_POINTER = -1

open class BetterRecyclerView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyle: Int = 0
) : RecyclerView(context, attrs, defStyle) {

    private var scrollPointerId = INVALID_POINTER
    private var initialTouchX: Int = 0
    private var initialTouchY: Int = 0

    private var touchSlop = ViewConfiguration.get(context).scaledTouchSlop

    override fun setScrollingTouchSlop(slopConstant: Int) {
        super.setScrollingTouchSlop(slopConstant)
        val vc = ViewConfiguration.get(context)
        when (slopConstant) {
            RecyclerView.TOUCH_SLOP_DEFAULT -> touchSlop = vc.scaledTouchSlop
            RecyclerView.TOUCH_SLOP_PAGING -> touchSlop = vc.scaledPagingTouchSlop
            else -> {
            }
        }
    }

    override fun onInterceptTouchEvent(e: MotionEvent?): Boolean {
        e ?: return super.onInterceptTouchEvent(e)

        val action = e.actionMasked
        val actionIndex = e.actionIndex

        when (action) {
            MotionEvent.ACTION_DOWN -> {
                scrollPointerId = e.getPointerId(0)
                initialTouchX = (e.x + 0.5f).toInt()
                initialTouchY = (e.y + 0.5f).toInt()
                return super.onInterceptTouchEvent(e)
            }

            MotionEvent.ACTION_POINTER_DOWN -> {
                scrollPointerId = e.getPointerId(actionIndex)
                initialTouchX = (e.getX(actionIndex) + 0.5f).toInt()
                initialTouchY = (e.getY(actionIndex) + 0.5f).toInt()
                return super.onInterceptTouchEvent(e)
            }

            MotionEvent.ACTION_MOVE -> {
                val index = e.findPointerIndex(scrollPointerId)
                if (index < 0) {
                    return false
                }

                val x = (e.getX(actionIndex) + 0.5f).toInt()
                val y = (e.getY(actionIndex) + 0.5f).toInt()
                if (scrollState != RecyclerView.SCROLL_STATE_DRAGGING) {
                    val dx = x - initialTouchX
                    val dy = y - initialTouchY
                    val canScrollHorizontally = layoutManager?.canScrollHorizontally() ?: false
                    val canScrollVertically = layoutManager?.canScrollVertically() ?: false
                    var startScroll = false
                    if (canScrollHorizontally && Math.abs(dx) > touchSlop && (Math.abs(dx) >= Math.abs(dy) || canScrollVertically)) {
                        startScroll = true
                    }
                    if (canScrollVertically && Math.abs(dy) > touchSlop && (Math.abs(dy) >= Math.abs(dx) || canScrollHorizontally)) {
                        startScroll = true
                    }
                    return startScroll && super.onInterceptTouchEvent(e)
                }
                return super.onInterceptTouchEvent(e)
            }
            else -> return super.onInterceptTouchEvent(e)
        }
    }
}