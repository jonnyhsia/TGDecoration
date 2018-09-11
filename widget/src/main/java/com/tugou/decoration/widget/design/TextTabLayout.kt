package com.tugou.decoration.widget.design

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.ShapeDrawable
import android.os.Build
import android.util.AttributeSet
import android.util.Log
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.viewpager.widget.ViewPager
import com.tugou.andromeda.kit.ui.dp2px
import com.tugou.andromeda.kit.ui.px2sp
import com.tugou.decoration.widget.R

class TextTabLayout @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    /** Tab 间距 */
    // private val tabSpacing: Float

    /** 选中的文字大小 */
    private val _tabSelectedSize: Float

    /** 文字默认大小 */
    private val _tabTextSize: Float

    /** 文字默认颜色 */
    @ColorInt
    private val _tabTextColor: Int

    /** 选中的文字颜色 */
    @ColorInt
    private val _textTextSelectedColor: Int

    /** 选中的 tab 位置 */
    private var _selectPos: Int = 0

    /** Tab 选中监听器 */
    var selectChangedListener: TabSelectChangedListener? = null

    /** Tab Item View 数组 */
    private var tabList = ArrayList<TextView>()

    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.TextTabLayout)

        _tabSelectedSize = context.px2sp(typedArray.getDimensionPixelSize(R.styleable.TextTabLayout_tg_tabTextSelectedSize, 32).toFloat())
        _tabTextSize = context.px2sp(typedArray.getDimensionPixelSize(R.styleable.TextTabLayout_tg_tabTextSize, 24).toFloat())
        _tabTextColor = typedArray.getColor(R.styleable.TextTabLayout_tg_tabTextColor, Color.BLACK)
        _textTextSelectedColor = typedArray.getColor(R.styleable.TextTabLayout_tg_tabTextSelectedColor, Color.BLACK)

        // 通过 spacing 设置 layout divider
        val tabSpacing = typedArray.getDimension(R.styleable.TextTabLayout_tg_tabSpacing, context.dp2px(20f))
        dividerDrawable = ShapeDrawable().apply {
            paint.color = Color.TRANSPARENT
            intrinsicWidth = tabSpacing.toInt()
        }
        showDividers = SHOW_DIVIDER_MIDDLE

        typedArray.recycle()

        isBaselineAligned = true
    }

    /**
     * 设置 TabLayout 的数据
     */
    fun setTabs(tabTextList: List<String>) {
        tabList.clear()
        removeAllViews()

        val lp = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT)

        tabTextList.forEachIndexed { pos, tabText ->
            TextView(context)
                    .apply {
                        text = tabText
                        textSize = _tabTextSize
                        setTextColor(_tabTextColor)
                        layoutParams = lp
                        includeFontPadding = false
                        gravity = Gravity.CENTER
                        typeface = Typeface.DEFAULT_BOLD
                        setOnClickListener {
                            if (!isSelected) selectTab(pos)
                        }

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            letterSpacing = 0.03f
                        }
                    }
                    .also {
                        addView(it)
                        tabList.add(it)
                    }
        }

        // 设置默认选中
        tabList[0].run {
            textSize = _tabSelectedSize
            setTextColor(_textTextSelectedColor)
        }
        _selectPos = 0
    }

    /**
     * 选中tab
     * 放大 选中Tab 字体 改变 颜色
     * 缩小 之前选中Tab 字体 改变 颜色
     * @param pos 位置
     */
    fun selectTab(pos: Int) {
        if (pos >= tabList.size && pos < 0) {
            Log.w("TextTabLayout", "非法 Position: $pos.")
        } else {
            // Tab 选中回调
            selectChangedListener?.onTabSelectChanged(_selectPos, pos)

            // 修改之前选中Tab 属性
            tabList[_selectPos].apply {
                isSelected = false
                setTextColor(_tabTextColor)
            }.also {
                ObjectAnimator.ofFloat(it, "textSize", context.px2sp(it.textSize), _tabTextSize)
                        .setDuration(150)
                        .start()
            }

            // 修改当前选中Tab 属性
            tabList[pos].apply {
                isSelected = true
                setTextColor(_textTextSelectedColor)
            }.also {
                ObjectAnimator.ofFloat(it, "textSize", context.px2sp(it.textSize), _tabSelectedSize)
                        .setDuration(150)
                        .start()
            }

            _selectPos = pos
            // 切换 ViewPager
            attachedViewPager?.setCurrentItem(pos, true)
        }
    }

    /**
     * 获得当前Tab
     * @param pos 位置
     */
    fun getTabAt(pos: Int): TextView? {
        return if (pos < 0 || pos >= tabList.size) null else tabList[pos]
    }

    fun getSelectedTabIndex() = _selectPos

    private var attachedViewPager: ViewPager? = null

    var isAttachedToPager = attachedViewPager != null

    /**
     * 绑定ViewPager
     */
    fun setUpWithViewPager(vp: ViewPager) {
        attachedViewPager = vp
        val adapter = vp.adapter ?: throw RuntimeException()
        val titleList = ArrayList<String>()

        for (pos in 0 until adapter.count) {
            titleList.add("${adapter.getPageTitle(pos)}")
        }

        setTabs(titleList)

        vp.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {
                if (_selectPos != position) {
                    selectTab(position)
                }
            }
        })
    }


    interface TabSelectChangedListener {

        /**
         * tab选中回调
         * @param oldPos 原先的位置
         * @param newPos 新选中的位置
         */
        fun onTabSelectChanged(oldPos: Int, newPos: Int)

    }
}