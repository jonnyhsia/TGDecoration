package com.tugou.decoration.widget.recyclerview

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import me.everything.android.ui.overscroll.HorizontalOverScrollBounceEffectDecorator
import me.everything.android.ui.overscroll.IOverScrollDecor
import me.everything.android.ui.overscroll.VerticalOverScrollBounceEffectDecorator
import me.everything.android.ui.overscroll.adapters.RecyclerViewOverScrollDecorAdapter

class BounceRecyclerView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyle: Int = 0
) : BetterRecyclerView(context, attrs, defStyle) {

    private var overScrollDecor: IOverScrollDecor? = null

    override fun setLayoutManager(layout: RecyclerView.LayoutManager?) {
        super.setLayoutManager(layout)
        if (isInEditMode) {
            return
        }
        if (layout is LinearLayoutManager) {
            overScrollDecor = if (layout.orientation == LinearLayoutManager.HORIZONTAL) {
                HorizontalOverScrollBounceEffectDecorator(RecyclerViewOverScrollDecorAdapter(this), 2f, 1f, -2f)
            } else {
                VerticalOverScrollBounceEffectDecorator(RecyclerViewOverScrollDecorAdapter(this), 2f, 1f, -2f)
            }
        }
    }
}