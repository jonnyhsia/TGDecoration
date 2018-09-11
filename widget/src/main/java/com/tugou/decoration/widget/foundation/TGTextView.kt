package com.tugou.decoration.widget.foundation

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.res.ResourcesCompat
import com.tugou.decoration.widget.R

private const val FONT_REGULAR = 0
private const val FONT_MEDIUM = 1
private const val FONT_BOLD = 2

class TGTextView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0

) : AppCompatTextView(context, attrs, defStyleAttr) {

    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.TGTextView)
        when (typedArray.getInt(R.styleable.TGTextView_tg_font, FONT_REGULAR)) {
            FONT_MEDIUM -> {
                typeface = if (isInEditMode) {
                    ResourcesCompat.getFont(context, R.font.noto_sans_medium)
                } else {
                    TGFont.MEDIUM
                }
            }
            FONT_BOLD -> {
                typeface = Typeface.DEFAULT_BOLD
            }
            else -> {
                // default font
            }
        }
        typedArray.recycle()

        includeFontPadding = false
    }
}