package com.tugou.decoration.widget.design

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.CheckedTextView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.widget.TextViewCompat
import com.tugou.andromeda.kit.ui.dp2px
import com.tugou.decoration.widget.R

data class NavigationItem(
        @DrawableRes val icon: Int,
        @StringRes val text: Int
)

class BottomNavigation @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    lateinit var navigationListener: (lastPos: Int, pos: Int) -> Unit

    private val textColorStateList: ColorStateList
    private lateinit var itemViews: List<CheckedTextView>
    private var lastSelectedPosition: Int = -1
    private val navDivider: Int
    private val textAppearance: Int

    private val rippleRes: Int

    init {
        val a = context.obtainStyledAttributes(attrs, R.styleable.BottomNavigation)
        textColorStateList = a.getColorStateList(R.styleable.BottomNavigation_tg_textColor) ?: ColorStateList.valueOf(Color.BLACK)
        rippleRes = a.getResourceId(R.styleable.BottomNavigation_tg_ripple, -1)
        navDivider = a.getResourceId(R.styleable.BottomNavigation_tg_navDivider, -1)
        textAppearance = a.getResourceId(R.styleable.BottomNavigation_tg_textAppearance, -1)
        a.recycle()
    }

    fun performSelected(position: Int) {
        itemViews[position].performClick()
    }

    fun setNavigationItems(items: List<NavigationItem>) {
        removeAllViews()

        val tempItemViews = ArrayList<CheckedTextView>(items.size)

        for ((icon, textRes) in items) {
            val iconWidth = context.dp2px(32f).toInt()
            val iconHeight = context.dp2px(32f).toInt()
            val iconPadding = context.dp2px(1f).toInt()

            val drawable = context.getDrawable(icon)!!.apply {
                setBounds(0, 0, iconWidth, iconHeight)
            }

            CheckedTextView(context).apply {
                id = View.generateViewId()
                textAppearance.takeIf { it != -1 }?.let { TextViewCompat.setTextAppearance(this, it) }
                textSize = 10f
                textAlignment = TextView.TEXT_ALIGNMENT_CENTER
                compoundDrawablePadding = iconPadding
                setText(textRes)
                setBackgroundResource(rippleRes)
                setTextColor(textColorStateList)
                setCompoundDrawablesRelative(null, drawable, null, null)
                setOnClickListener {
                    onItemSelected(itemViews.indexOf(this))
                }
            }.also {
                tempItemViews.add(it)
                addView(it)
            }
        }

        itemViews = tempItemViews
        makeItemConstraints()

        if (navDivider != -1) {
            val view = View(context)
            view.background = ContextCompat.getDrawable(context, navDivider)
            addView(view)

            (view.layoutParams as ConstraintLayout.LayoutParams).apply {
                height = context.dp2px(1f).toInt()
                width = 0
                topToTop = ConstraintLayout.LayoutParams.PARENT_ID
                startToStart = ConstraintLayout.LayoutParams.PARENT_ID
                endToEnd = ConstraintLayout.LayoutParams.PARENT_ID
            }
        }
    }

    /**
     * 为 ItemViews 简历约束
     */
    private fun makeItemConstraints() {
        val parent = id
        val edgeInset = context.dp2px(8f).toInt()

        itemViews.forEachIndexed { index, view ->
            with(view.layoutParams as ConstraintLayout.LayoutParams) {
                height = WRAP_CONTENT
                width = 0

                topToTop = parent
                bottomToBottom = parent

                if (index == 0) {
                    horizontalChainStyle = ConstraintLayout.LayoutParams.CHAIN_SPREAD_INSIDE
                    startToStart = parent
                    marginStart = edgeInset
                } else {
                    startToEnd = itemViews[index - 1].id
                }

                if (index == itemViews.size - 1) {
                    endToEnd = parent
                    marginEnd = edgeInset
                } else {
                    endToStart = itemViews[index + 1].id
                }
            }
        }
    }

    private fun onItemSelected(position: Int) {
        if (position == lastSelectedPosition) {
            return
        }

        if (lastSelectedPosition != -1) {
            itemViews[lastSelectedPosition].isChecked = false
        }
        itemViews[position].isChecked = true
        navigationListener(lastSelectedPosition, position)
        lastSelectedPosition = position
    }
}