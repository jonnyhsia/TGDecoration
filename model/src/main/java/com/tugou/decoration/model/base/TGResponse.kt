package com.tugou.decoration.model.base

data class TGResponse<T>(
        val code: Int,
        val message: String,
        val data: T?
)