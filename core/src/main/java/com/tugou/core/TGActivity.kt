package com.tugou.core

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import com.githang.statusbar.StatusBarCompat

abstract class TGActivity : AppCompatActivity(), BackPressObservable {

    companion object {
        @JvmStatic
        private val DEFAULT_STATUS_CONFIG = StatusConfig()
    }

    /** Activity Layout Res */
    @LayoutRes
    open val layoutRes: Int = R.layout.activity_common

    /** 状态栏的配置, 默认白底亮色状态栏 */
    internal open val statusConfig: StatusConfig = DEFAULT_STATUS_CONFIG

    /** 开启在缺口下绘制 View, 默认开启 */
    open val enableDrawNotch = true

    final override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutRes)

        setUpStatusBar()

        if (enableDrawNotch) {
            // TODO
        }

        onContentCreated(savedInstanceState)
    }

    /**
     * 设置状态栏
     */
    private fun setUpStatusBar() {
        with(statusConfig) {
            // 设置 layout 全屏
            if (isLayoutFullscreen) {
                window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            }
            when {
                isWindowFullscreen -> {
                    // TODO 全屏(无状态栏)
                }
                isTranslucent -> StatusBarCompat.setTranslucent(window, true)
                else -> StatusBarCompat.setStatusBarColor(window, statusBarColor, isLight)
            }
        }
    }

    /**
     * 当 ContentView 创建后执行
     */
    abstract fun onContentCreated(savedInstanceState: Bundle?)

    /**
     * 返回事件通知与处理
     */
    override fun onBackPressed() {
        for (observer in backPressObservers) {
            if (observer.onBackPressed()) {
                return
            }
        }
        super.onBackPressed()
    }
}