package com.tugou.decoration.model.base

open class ServerRespException(
        val code: Int,
        message: String
) : Exception(message)

class ExpiredException : ServerRespException(8001, "Token 已失效, 请重新登录")

class NullDataException : ServerRespException(1000, "数据为空")