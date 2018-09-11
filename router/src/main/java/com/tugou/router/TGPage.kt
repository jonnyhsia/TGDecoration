package com.tugou.router

class TGPage(
        val name: String,
        val targetClz: Class<*>,
        val univCode: Int = -1,
        val interceptors: Array<Class<*>> = emptyArray()
)