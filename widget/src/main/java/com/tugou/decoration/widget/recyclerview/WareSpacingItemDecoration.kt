package com.tugou.decoration.widget.recyclerview

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class WareSpacingItemDecoration(
        private val spacingSmall: Int,
        private val spacingBig: Int,
        private val isWare: (pos: Int) -> Boolean
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        if (!isWare(parent.getChildAdapterPosition(view))) {
            return
        }

        val lp = view.layoutParams as GridLayoutManager.LayoutParams
        val spanIndex = lp.spanIndex

        with(outRect) {
            if (spanIndex == 0) {
                left = spacingBig
                right = spacingSmall
            } else {
                left = spacingSmall
                right = spacingBig
            }
        }
    }
}