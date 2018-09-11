package com.tugou.core

sealed class RequestState {

    object StateFetching : RequestState()
    object StateSuccess : RequestState()
    object StateNetworkError : RequestState()

    class StateFailed(val message: String) : RequestState()

}