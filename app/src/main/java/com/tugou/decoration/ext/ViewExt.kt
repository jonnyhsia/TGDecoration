package com.tugou.decoration.ext

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.Group
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.AppBarLayout
import com.tugou.decoration.GlideApp
import com.tugou.decoration.GlideRequest

/**
 * 给 Group 中的 ChildView 添加点击监听
 */
fun Group.addOnClickListener(listener: View.OnClickListener) {
    referencedIds.forEach {
        rootView.findViewById<View>(it).setOnClickListener(listener)
    }
}

/**
 * 通过 ViewHolder ItemView 查找 View
 */
fun <T : View> RecyclerView.ViewHolder.findView(id: Int): T {
    return itemView.findViewById(id)
}

/**
 * 通过 ViewHolder 的 ItemView 获取 Context
 */
val RecyclerView.ViewHolder.context: Context
    get() = itemView.context

/**
 * 通过 Glide 给 ImageView 设置图片
 */
inline fun ImageView.load(model: Any, option: GlideRequest<Drawable>.() -> Unit) {
    val request = GlideApp.with(this).load(model)
    request.apply(option).into(this)
}

/**
 * 禁用 AppBar 的拖拽效果
 */
fun AppBarLayout.disableDrag() {
    val appBarBehavior = AppBarLayout.Behavior().apply {
        setDragCallback(object : AppBarLayout.Behavior.DragCallback() {
            override fun canDrag(layout: AppBarLayout): Boolean {
                return false
            }
        })
    }
    (layoutParams as CoordinatorLayout.LayoutParams).behavior = appBarBehavior
}

fun View.clipToCardOutline(cardRadius: Int) {
    clipToOutline = true
    outlineProvider = when (cardRadius) {
        4 -> Outlines.CARD_BOUNDS_WITH_4DP_CORNER
        6 -> Outlines.CARD_BOUNDS_WITH_6DP_CORNER
        else -> null
    }
}

/**
 * 通过 Outline 裁切 View 并设置其不透明度
 */
//fun View.clipToOutlineWithCorner(radius: Float, alpha: Float = 1f) {
//    clipToOutline = true
//    outlineProvider = object : ViewOutlineProvider() {
//        override fun getOutline(view: View, outline: Outline) {
//            outline.setRoundRect(0, 0, view.measuredWidth, view.measuredHeight, radius)
//            outline.alpha = alpha
//        }
//    }
//}

/**
 * TextView 显示的文字, 当文字为 null 或 empty 则隐藏, 反之显示.
 */
var TextView.displayText: CharSequence?
    get() = text
    set(value) {
        if (value?.isNotEmpty() == true) {
            text = value
            visibility = View.VISIBLE
        } else {
            visibility = View.GONE
        }
    }

fun RecyclerView.asVerticalList() {
    setHasFixedSize(true)
    layoutManager = LinearLayoutManager(context)
}

fun RecyclerView.asHorizontalList(reverseLayout: Boolean = false) {
    setHasFixedSize(true)
    layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, reverseLayout)
}

fun RecyclerView.asGridList(spanCount: Int, orientation: Int = RecyclerView.VERTICAL, reverseLayout: Boolean = false) {
    setHasFixedSize(true)
    layoutManager = GridLayoutManager(context, spanCount, orientation, reverseLayout)
}