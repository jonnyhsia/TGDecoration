package com.tugou.andromeda.kit.commander

import java.util.*

class TGActionMap : LinkedHashMap<String, TGAction?> {

    constructor()

    constructor(initialCapacity: Int) : super(initialCapacity)

    constructor(m: Map<out String, TGAction>) : super(m)

    internal fun peek(): TGAction? {
        return values.firstOrNull()
    }

    internal fun pop(): TGAction? {
        return keys.firstOrNull()?.let { remove(it) }
    }

    @JvmOverloads
    fun enqueue(name: String, action: TGAction? = null) {
        put(name, action)
    }
}
