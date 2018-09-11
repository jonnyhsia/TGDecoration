package com.tugou.andromeda.kit.app

import android.view.View
import android.view.ViewTreeObserver
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import java.lang.ref.WeakReference

class KeyboardWatcher(activity: AppCompatActivity) : LifecycleObserver {

    private val rootViewRef: WeakReference<View>
    private val viewTreeObserverListener: ViewTreeObserver.OnGlobalLayoutListener
    private var keyboardToggleListener: OnKeyboardToggleListener? = null

    init {
        // 观察 Activity 生命周期, 在 Activity 销毁时移除 Layout 监听
        activity.lifecycle.addObserver(this)

        // 软键盘输入模式必须是 Adjust Resize, 否则无法监听到窗口大小的变动
        val hasAdjustResizeInputMode = (activity.window.attributes.softInputMode
                and WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE) != 0

        if (hasAdjustResizeInputMode) {
            viewTreeObserverListener = GlobalLayoutListener()
            val contentView = activity.findViewById<View>(Window.ID_ANDROID_CONTENT)
            contentView.viewTreeObserver.addOnGlobalLayoutListener(viewTreeObserverListener)
            // 需要持有 rootView 的弱引用, 在销毁时移除其监听
            rootViewRef = WeakReference(contentView)
        } else {
            throw IllegalArgumentException(String.format("Activity should have windowSoftInputMode=\"adjustResize\"" + "to make KeyboardWatcher working. You can set it in AndroidManifest.xml"))
        }
    }

    fun setListener(keyboardToggleListener: OnKeyboardToggleListener) {
        this.keyboardToggleListener = keyboardToggleListener
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    private fun destroy() {
        rootViewRef.get()?.viewTreeObserver?.removeOnGlobalLayoutListener(viewTreeObserverListener)
    }

    private inner class GlobalLayoutListener : ViewTreeObserver.OnGlobalLayoutListener {
        internal var initialValue: Int = 0
        internal var hasSentInitialAction: Boolean = false
        internal var isKeyboardShown: Boolean = false

        override fun onGlobalLayout() {
            val rootView = rootViewRef.get() ?: return

            if (initialValue == 0) {
                initialValue = rootView.height
            } else {
                if (initialValue > rootView.height) {
                    if (!hasSentInitialAction || !isKeyboardShown) {
                        isKeyboardShown = true
                        keyboardToggleListener?.onKeyboardShown(initialValue - rootView.height)
                    }
                } else {
                    if (!hasSentInitialAction || isKeyboardShown) {
                        isKeyboardShown = false
                        rootView.post {
                            keyboardToggleListener?.onKeyboardClosed()
                        }
                    }
                }
                hasSentInitialAction = true
            }
        }
    }

    interface OnKeyboardToggleListener {

        fun onKeyboardShown(keyboardSize: Int)

        fun onKeyboardClosed()
    }
}