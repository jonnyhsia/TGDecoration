package com.tugou.core

sealed class HubMessage {

    data class DisplayMsg(
            val text: String
    ) : HubMessage()

    data class RouteMsg(
            val url: String
    ) : HubMessage()

}