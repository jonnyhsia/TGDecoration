package com.tugou.core

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

open class TGViewModel : ViewModel(), Corgi {

    protected val disposables = CompositeDisposable()

    /** 可观察的网络请求状态 */
    val response = MutableLiveData<RequestState>()

    /** HubMessage 的中心 */
    val messageHub = MutableLiveData<HubMessage>()

    fun navigate(url: String) {
        // 往 message hub 中发送一条路由的消息
        messageHub.postValue(HubMessage.RouteMsg(url))
    }

    fun toast(text: String?) {
        text ?: return
        // 往 message hub 中发送一条展示的消息
        messageHub.postValue(HubMessage.DisplayMsg(text))
    }

    override fun onCleared() {
        disposables.clear()
        super.onCleared()
    }
}