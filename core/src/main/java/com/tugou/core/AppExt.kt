package com.tugou.core

import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * 获取使用默认构造器的 ViewModel
 */
inline fun <reified T : ViewModel> Fragment.getViewModel(): T {
    return ViewModelProviders.of(this).get(T::class.java)
}

/**
 * 获取使用自定义构造器的 ViewModel
 */
@Suppress("UNCHECKED_CAST")
inline fun <reified T : ViewModel> Fragment.getViewModelOf(
        crossinline viewModelCreator: () -> T
): T {
    val factory = object : ViewModelProvider.NewInstanceFactory() {
        override fun <VM : ViewModel?> create(modelClass: Class<VM>): VM {
            return viewModelCreator() as VM
        }
    }
    return ViewModelProviders.of(this, factory).get(T::class.java)
}

/**
 * 获取共享的默认构造器 ViewModel
 */
inline fun <reified T : ViewModel> Fragment.getSharedViewModel(): T {
    return ViewModelProviders.of(requireActivity()).get(T::class.java)
}

/**
 * 获取共享的自定义构造器的 ViewModel
 */
@Suppress("UNCHECKED_CAST")
inline fun <reified T : ViewModel> Fragment.getSharedViewModelOf(
        crossinline viewModelCreator: () -> T
): T {
    val factory = object : ViewModelProvider.NewInstanceFactory() {
        override fun <VM : ViewModel?> create(modelClass: Class<VM>): VM {
            return viewModelCreator() as VM
        }
    }
    return ViewModelProviders.of(requireActivity(), factory).get(T::class.java)
}

/**
 * 将 Disposable 添加到 CompositeDisposable
 */
fun Disposable.addTo(disposables: CompositeDisposable) {
    disposables.add(this)
}

/**
 * 观察 Live Data
 */
inline fun <T> LiveData<T>.observe(lifecycleOwner: LifecycleOwner,
                                   crossinline observer: (T?) -> Unit) {
    observe(lifecycleOwner, Observer {
        observer(it)
    })
}