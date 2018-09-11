package com.tugou.decoration.widget.foundation

import android.content.Context
import android.graphics.Typeface
import androidx.core.content.res.ResourcesCompat
import com.tugou.decoration.widget.R

object TGFont {
    var MEDIUM: Typeface? = null

    fun initialize(context: Context) {
        if (MEDIUM == null) {
            MEDIUM = ResourcesCompat.getFont(context, R.font.noto_sans_medium)
        }
    }
}