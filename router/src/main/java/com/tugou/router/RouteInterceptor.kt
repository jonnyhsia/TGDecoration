package com.tugou.router

/**
 * 路由跳转的拦截器
 */
interface RouteInterceptor {

    fun intercept(intent: RouterIntent.NavigateIntent): RouterIntent.NavigateIntent
}