package com.tugou.decoration.model.passport.entity

data class UserModel(
        val userId: Int,
        val token: String,
        val username: String,
        val avatar: String,
        val tel: String,
        val isVip: Boolean = false,
        val wxUnionId: String? = null
)