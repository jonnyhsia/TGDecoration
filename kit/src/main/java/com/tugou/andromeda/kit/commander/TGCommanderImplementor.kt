package com.tugou.andromeda.kit.commander

import java.util.*

class TGCommanderImplementor {
    internal val actionMap = LinkedHashMap<String, TGAction>()

    fun implement(name: String, action: TGAction) {
        actionMap[name] = action
    }
}
