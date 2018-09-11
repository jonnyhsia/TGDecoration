package com.tugou.decoration.ext

import android.graphics.Outline
import android.view.View
import android.view.ViewOutlineProvider
import com.tugou.andromeda.kit.ui.dp2px

object Outlines {

    val CARD_BOUNDS_WITH_4DP_CORNER = object : ViewOutlineProvider() {
        override fun getOutline(view: View, outline: Outline) {
            outline.alpha = 0.36f
            outline.setRoundRect(0, 0, view.measuredWidth, view.measuredHeight, application.dp2px(4f))
        }
    }

    val CARD_BOUNDS_WITH_6DP_CORNER = object : ViewOutlineProvider() {
        override fun getOutline(view: View, outline: Outline) {
            outline.alpha = 0.36f
            outline.setRoundRect(0, 0, view.measuredWidth, view.measuredHeight, application.dp2px(6f))
        }
    }
}