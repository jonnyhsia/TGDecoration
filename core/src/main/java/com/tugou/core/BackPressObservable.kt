package com.tugou.core

import java.util.*

interface BackPressObservable {

    val backPressObservers: TreeSet<BackPressObserver>
        get() = TreeSet()

    fun addBackPressObserver(observer: BackPressObserver) {
        backPressObservers.add(observer)
    }
}

abstract class BackPressObserver(
        private val priority: Int = 0
) : Comparable<BackPressObserver> {

    /**
     * @return 是否消费此次返回事件
     */
    abstract fun onBackPressed(): Boolean

    /**
     * 按优先级降序排列
     */
    override fun compareTo(other: BackPressObserver) = if (priority > other.priority) -1 else 1
}