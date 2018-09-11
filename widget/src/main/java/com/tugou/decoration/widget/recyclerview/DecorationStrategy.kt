package com.tugou.decoration.widget.recyclerview

interface DecorationStrategy {
    /**
     * @return 返回 position 对应的 Item 是否需要绘制 Decoration
     */
    fun shouldDraw(position: Int): Boolean
}