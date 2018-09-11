package com.tugou.decoration.widget.recyclerview

import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.view.View
import androidx.annotation.Px
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

const val DIVIDER_BEGINNING = 1
const val DIVIDER_MIDDLE = 2
const val DIVIDER_END = 4

class SpacingItemDecoration(
        @Px private val left: Int,
        @Px private val top: Int,
        @Px private val right: Int,
        @Px private val bottom: Int
) : RecyclerView.ItemDecoration() {

    constructor(@Px spacing: Int) : this(spacing, spacing, spacing, spacing)

    constructor(@Px horizontalSpacing: Int, @Px verticalSpacing: Int) : this(horizontalSpacing, verticalSpacing, horizontalSpacing, verticalSpacing)

    /** 绘制间距的策略 */
    var decorationStrategy: ((pos: Int) -> Boolean)? = null

    /** 在边缘处只绘制一半大小的间距 */
    var edgeSpacingFactory = 1f

    /** 如果 RecyclerView 含有 Header, 需要传入 Header Count */
    var headerCount: Int = 0

    var divider: Drawable? = null

    var drawDivider = DIVIDER_MIDDLE

    var dividerInset: Pair<Int, Int>? = null

    private val bounds = Rect()

    override fun onDraw(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val divider = this.divider ?: return

        canvas.save()

        val left: Int
        val right: Int

        val (insetLeft, insetRight) = dividerInset ?: (0 to 0)

        if (parent.clipToPadding) {
            left = parent.paddingLeft + insetLeft
            right = parent.width - parent.paddingRight - insetRight
            canvas.clipRect(left, parent.paddingTop,
                    right, parent.height - parent.paddingBottom)
        } else {
            left = 0 + insetLeft
            right = parent.width + insetRight
        }

        if (drawDivider and DIVIDER_MIDDLE != 0) {
            val count = parent.childCount
            for (i in headerCount until count) {
                if (i == headerCount && drawDivider and DIVIDER_BEGINNING == 0)
                    continue
                if (i == count - 1 && drawDivider and DIVIDER_END == 0)
                    continue

                val child = parent.getChildAt(i)
                parent.getDecoratedBoundsWithMargins(child, bounds)

                val bottom = bounds.bottom + Math.round(child.translationY)
                val top = bottom - divider.intrinsicHeight
                divider.setBounds(left, top, right, bottom)
                divider.draw(canvas)
            }
        }

        canvas.restore()
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        when (parent.layoutManager) {
            is GridLayoutManager -> getGridItemOffsets(outRect, view, parent)
            is LinearLayoutManager -> getLinearItemOffset(outRect, view, parent)
            is StaggeredGridLayoutManager -> getStaggeredItemOffset(outRect, view, parent)
        }
    }

    private fun getLinearItemOffset(outRect: Rect, view: View, parent: RecyclerView) {
        val position = parent.getChildAdapterPosition(view)
        val isVertical = (parent.layoutManager as LinearLayoutManager).orientation == LinearLayoutManager.VERTICAL

        // pass when the expression returns null or true
        if (decorationStrategy?.invoke(position) == false) {
            return
        }

        // 如果是第一个
        if (position - headerCount == 0) {
            outRect.setTopWithOrientation(top * edgeSpacingFactory, left * edgeSpacingFactory, isVertical)
        } else {
            outRect.setTopWithOrientation(top, left, isVertical)
        }

        outRect.setLeftWithOrientation(left * edgeSpacingFactory, top * edgeSpacingFactory, isVertical)
        outRect.setRightWithOrientation(right * edgeSpacingFactory, bottom * edgeSpacingFactory, isVertical)

        if (position == parent.adapter!!.itemCount - 1) {
            outRect.setBottomWithOrientation(bottom * edgeSpacingFactory, right * edgeSpacingFactory, isVertical)
        } else {
            outRect.setBottomWithOrientation(bottom, right, isVertical)
        }
    }

    private fun getGridItemOffsets(outRect: Rect, view: View, parent: RecyclerView) {
        val position = parent.getChildAdapterPosition(view)
        val isVertical = (parent.layoutManager as GridLayoutManager).orientation == GridLayoutManager.VERTICAL
        val lp = view.layoutParams as GridLayoutManager.LayoutParams
        val spanIndex = lp.spanIndex
        val spanCount = (parent.layoutManager as GridLayoutManager).spanCount

        // pass when the expression returns null or true
        if (decorationStrategy?.invoke(position) == false) {
            return
        }

        if (position - headerCount < spanCount) {
            outRect.setTopWithOrientation(top * edgeSpacingFactory, left * edgeSpacingFactory, isVertical)
        } else {
            outRect.setTopWithOrientation(top, left, isVertical)
        }

        if (position + spanCount >= parent.adapter!!.itemCount) {
            outRect.setBottomWithOrientation(bottom * edgeSpacingFactory, right * edgeSpacingFactory, isVertical)
        } else {
            outRect.setBottomWithOrientation(bottom, right, isVertical)
        }

        if (spanIndex == 0) {
            outRect.setLeftWithOrientation((left + right) * edgeSpacingFactory / 2, (top + bottom) * edgeSpacingFactory / 2, isVertical)
        } else {
            outRect.setLeftWithOrientation(left, top, isVertical)
        }

        if (spanIndex == spanCount - 1) {
            outRect.setRightWithOrientation((left + right) * edgeSpacingFactory / 2, (top + bottom) * edgeSpacingFactory / 2, isVertical)
        } else {
            // 非最后一列的右边距为左右边距的一半
            outRect.setRightWithOrientation(right, bottom, isVertical)
        }
    }

    private fun getStaggeredItemOffset(outRect: Rect, view: View, parent: RecyclerView) {
        val position = parent.getChildAdapterPosition(view)
        val isVertical = (parent.layoutManager as StaggeredGridLayoutManager).orientation == StaggeredGridLayoutManager.VERTICAL
        val spanCount = (parent.layoutManager as StaggeredGridLayoutManager).spanCount

        // pass when the expression returns null or true
        if (decorationStrategy?.invoke(position) == false) {
            return
        }

        val lp = view.layoutParams as StaggeredGridLayoutManager.LayoutParams
        val spanIndex = lp.spanIndex

        // left edge
        if (spanIndex == 0) {
            outRect.setLeftWithOrientation(left * edgeSpacingFactory, top * edgeSpacingFactory, isVertical)
        } else {
            outRect.setLeftWithOrientation(left, top, isVertical)
        }

        // top edge
        if (position - headerCount < spanCount) {
            outRect.setTopWithOrientation(top * edgeSpacingFactory, left * edgeSpacingFactory, isVertical)
        } else {
            outRect.setTopWithOrientation(top, left, isVertical)
        }

        // right edge
        if (spanIndex == spanCount - 1) {
            outRect.setRightWithOrientation(right * edgeSpacingFactory, bottom * edgeSpacingFactory, isVertical)
        } else {
            outRect.setRightWithOrientation(right, bottom, isVertical)
        }

        // bottom edge
        // FIXME: bottom of each span
        if (position == parent.adapter!!.itemCount - 1) {
            outRect.setBottomWithOrientation(bottom * edgeSpacingFactory, right * edgeSpacingFactory, isVertical)
        } else {
            outRect.setBottomWithOrientation(bottom, right, isVertical)
        }
    }
}

private fun Rect.setBottomWithOrientation(bottom: Number, right: Number, isVertical: Boolean) {
    if (isVertical) {
        this.bottom = bottom.toInt()
    } else {
        this.right = right.toInt()
    }
}

private fun Rect.setRightWithOrientation(right: Number, bottom: Number, isVertical: Boolean) {
    if (isVertical) {
        this.right = right.toInt()
    } else {
        this.bottom = bottom.toInt()
    }
}

private fun Rect.setLeftWithOrientation(left: Number, top: Number, isVertical: Boolean) {
    if (isVertical) {
        this.left = left.toInt()
    } else {
        this.top = top.toInt()
    }
}

private fun Rect.setTopWithOrientation(top: Number, left: Number, isVertical: Boolean) {
    if (isVertical) {
        this.top = top.toInt()
    } else {
        this.left = left.toInt()
    }
}