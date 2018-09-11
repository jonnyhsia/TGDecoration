package com.tugou.decoration

import android.app.Application
import android.os.Looper
import com.squareup.leakcanary.LeakCanary
import com.tugou.core.Corgi
import com.tugou.decoration.base.LoginInterceptor
import com.tugou.decoration.model.Repository
import com.tugou.decoration.page.home.MainActivity
import com.tugou.decoration.page.inbox.InboxActivity
import com.tugou.decoration.page.login.LoginActivity
import com.tugou.decoration.widget.foundation.TGFont
import com.tugou.router.TGPage
import com.tugou.router.TGRouter
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlin.properties.Delegates

class DecorApp : Application() {

    companion object {
        var INSTANCE: DecorApp by Delegates.notNull()
            private set
    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this

        Repository.initialize(applicationContext)
        TGFont.initialize(applicationContext)
        Corgi.loggable = BuildConfig.DEBUG
        initializeRouter()

        RxAndroidPlugins.setMainThreadSchedulerHandler {
            AndroidSchedulers.from(Looper.getMainLooper(), true)
        }

        if (BuildConfig.DEBUG) {
            LeakCanary.install(this)
        }
    }

    private fun initializeRouter() {
        TGRouter.apply {
            registerSchemas("decor", "app")
            resgiterSchemaWithCustomBehavior("https") {
                // TODO 跳转到统一的内嵌页
            }
            resgiterSchemaWithCustomBehavior("system") {
                // TODO 跳转到对应的系统页面 (如开启通知/权限/等)
            }
            registerPages(providePages())
            registerInterceptor(LoginInterceptor())
        }
    }

    private fun providePages() = listOf(
            TGPage("Main", MainActivity::class.java),
            TGPage("Login", LoginActivity::class.java, univCode = 10000),
            TGPage("Inbox", InboxActivity::class.java, interceptors = arrayOf(LoginInterceptor::class.java))
    )
}