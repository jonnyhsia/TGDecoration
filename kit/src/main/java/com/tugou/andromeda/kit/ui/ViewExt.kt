@file:JvmName("ViewKit")

package com.tugou.andromeda.kit.ui

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

/**
 * 获取是否有 BottomNavigationBar
 */
val Context.isNavBarOnBottom: Boolean
    get() {
        val cfg = resources.configuration
        val dm = resources.displayMetrics
        val canMove = dm.widthPixels != dm.heightPixels && cfg.smallestScreenWidthDp < 600
        return !canMove || dm.widthPixels < dm.heightPixels
    }

/**
 * 显示键盘
 */
@JvmOverloads
fun View.showKeyboard(flags: Int = InputMethodManager.SHOW_IMPLICIT) {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
    imm?.showSoftInput(this, flags)
}

/**
 * 隐藏键盘
 */
fun View.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
    imm?.hideSoftInputFromWindow(windowToken, 0)
}

/**
 * 在缺口屏设备上给 View 添加额外的上边距
 */
fun View.fitNotchByPadding() {
    setPaddingRelative(paddingStart, paddingTop + context.statusBarHeight, paddingEnd, paddingBottom)
}