package com.tugou.decoration.base

import android.widget.Toast
import androidx.core.net.toUri
import com.tugou.decoration.model.Repository
import com.tugou.router.RouteInterceptor
import com.tugou.router.RouterIntent

class LoginInterceptor : RouteInterceptor {

    override fun intercept(intent: RouterIntent.NavigateIntent): RouterIntent.NavigateIntent {
        return if (Repository.passportDataSource.isUserLogin()) {
            intent
        } else {
            Toast.makeText(intent.context, "被拦截到登录页", Toast.LENGTH_SHORT).show()
            RouterIntent.NavigateIntent(intent.contextable, "decor://Login".toUri())
        }
    }
}