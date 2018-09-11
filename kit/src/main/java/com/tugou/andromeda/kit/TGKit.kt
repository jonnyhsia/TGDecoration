package com.tugou.andromeda.kit

import androidx.collection.ArrayMap
import java.util.*

object TGKit {

    internal val INJECTED_APP_PARAMS = ArrayMap<String, String>()

    internal var INJECTED_USER_PARAMS: () -> Map<String, String> = {
        Collections.emptyMap()
    }

    /**
     * 注入静态 Url 参数, 用于 [String.toTGUrl]
     *
     * 如: from=app&app_version=4.7.1&app_name=tugou&utm_term=google
     */
    @JvmStatic
    fun injectStaticAppUrlParameters(parameters: Map<String, String>) {
        INJECTED_APP_PARAMS.putAll(parameters)
    }

    /**
     * 注入动态 Url 参数, 用于
     */
    @JvmStatic
    fun injectDynamicUserParameters(injector: () -> Map<String, String>) {
        INJECTED_USER_PARAMS = injector
    }
}