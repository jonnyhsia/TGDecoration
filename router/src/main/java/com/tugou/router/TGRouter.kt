package com.tugou.router

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.collection.ArrayMap
import androidx.collection.ArraySet
import androidx.collection.SparseArrayCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

typealias SchemaProcessor = (RouterIntent.NavigateIntent) -> Unit

object TGRouter {

    /** 合法的 Schema */
    private val validSchema = ArraySet<String>()
    /** 自定义行为的 Schema */
    private val customSchemaProcessorMap = ArrayMap<String, SchemaProcessor>()
    /** 通常的原生页 */
    private val nativePages = ArrayMap<String, TGPage>()
    /** 含有 Universal Code 的页面 */
    private val universalPages = SparseArrayCompat<TGPage>()
    /** 跳转的拦截器 */
    private val interceptors = ArrayMap<Class<*>, RouteInterceptor>()

    /**
     * 注册合法的 Schema
     */
    fun registerSchemas(vararg schemas: String) {
        validSchema.addAll(schemas)
    }

    /**
     * 注册自定义行为的 Schema
     *
     * @param schema    协议名
     * @param processor 自定义协议处理器
     */
    fun resgiterSchemaWithCustomBehavior(
            schema: String,
            processor: SchemaProcessor
    ) {
        validSchema.add(schema)
        customSchemaProcessorMap[schema] = processor
    }

    /**
     * 由 Fragment 发起页面跳转
     */
    fun navigate(fragment: Fragment, pageUri: String) {
        val contextable = object : Contextable() {
            override val self = fragment
            override val context = fragment.requireActivity()
        }
        val uri = Uri.parse(pageUri)
        RouterIntent.NavigateIntent(contextable, uri).also {
            navigateWithIntent(it)
        }
    }

    /**
     * 由 Context 发起页面跳转
     */
    fun navigate(context: Context, pageUri: String) {
        val contextable = object : Contextable() {
            override val self = context
            override val context = context
        }
        val uri = Uri.parse(pageUri)
        RouterIntent.NavigateIntent(contextable, uri).also {
            navigateWithIntent(it)
        }
    }

    fun navigateWithIntent(intent: RouterIntent.NavigateIntent) {
        val host = intent.uri.host ?: throw IllegalArgumentException("页面 Host 缺失")

        // 判断协议是否合法
        if (validSchema.contains(host)) {
            // 判断是否是自定义行为的协议
            val processor = customSchemaProcessorMap[host]
            if (processor == null) {
                val targetPage = nativePages[host]
                        ?: return intent.failed("检查页面是否配置")
                navigateNativePage(targetPage, intent)
            } else {
                processor(intent)
            }
        } else {
            intent.navigateCallback?.invoke(NavigateResult(false, "非法的跳转协议"))
        }
    }

    private fun navigateNativePage(page: TGPage, routerIntent: RouterIntent.NavigateIntent) {
        var mutableIntent = routerIntent
        // 遍历 page 设置的拦截器
        for (interceptorClz in page.interceptors) {
            val interceptor = interceptors[interceptorClz] ?: continue
            mutableIntent = interceptor.intercept(mutableIntent)
            // 若 mutable intent 无效, 则回调失败, 并结束跳转
            if (!mutableIntent.isValid) {
                mutableIntent.result?.let { routerIntent.navigateCallback?.invoke(it) }
                return
            }
        }

        // 设置 intent 与参数
        val intent = Intent(mutableIntent.contextable.context, page.targetClz)
        mutableIntent.extras?.let(intent::putExtras)
        with(mutableIntent.uri) {
            for (key in queryParameterNames) {
                getQueryParameter(key)?.let {
                    intent.putExtra(key, it)
                }
            }
        }

        // 判断是否需要中继跳转
        if (mutableIntent.relayIntents?.isNotEmpty() == true) {
            val relayIntents = ArrayList<Intent>(mutableIntent.relayIntents!!.size)
            for (relayIntent in mutableIntent.relayIntents!!) {
                val host = relayIntent.uri.host ?: continue
                val relayPage = nativePages[host] ?: continue
                relayIntents.add(Intent(mutableIntent.context, relayPage.targetClz))
            }
            val intents = Array(relayIntents.size + 1) {
                if (it == relayIntents.size) {
                    intent
                } else {
                    relayIntents[it]
                }
            }
            ContextCompat.startActivities(mutableIntent.context, intents)
            return
        }

        val contextSelf = mutableIntent.contextable.self
        when (contextSelf) {
            is Fragment -> {
                if (mutableIntent.requestCode > 0) {
                    contextSelf.startActivityForResult(intent, mutableIntent.requestCode)
                } else {
                    contextSelf.startActivity(intent)
                }
            }
            is Activity -> {
                if (mutableIntent.requestCode > 0) {
                    contextSelf.startActivityForResult(intent, mutableIntent.requestCode)
                } else {
                    contextSelf.startActivity(intent)
                }
            }
            is Context -> {
                ContextCompat.startActivity(contextSelf, intent, null)
            }
        }
    }

    /**
     * 添加路由拦截器
     */
    fun registerInterceptor(interceptor: RouteInterceptor) {
        interceptors[interceptor::class.java] = interceptor
    }

    /**
     * 往路由表中注册页面
     */
    fun registerPages(pages: List<TGPage>) {
        for (page in pages) {
            with(page) {
                nativePages[name] = this
                if (univCode != -1) {
                    universalPages.put(univCode, this)
                }
            }
        }
    }
}

sealed class RouterIntent(
        internal val uri: Uri
) {
    class NavigateIntent(
            val contextable: Contextable,
            uri: Uri,
            internal val requestCode: Int = -1
    ) : RouterIntent(uri) {
        var navigateCallback: ((NavigateResult) -> Unit)? = null

        internal var relayIntents: ArrayList<RelayIntent>? = null

        internal var extras: Bundle? = null

        val context: Context
            get() = contextable.context

        val isValid: Boolean
            get() = uri != Uri.EMPTY

        var result: NavigateResult? = null

        fun withRelay(relayUri: String) {
            if (relayIntents == null) {
                // 最多只能中继 3 个页面
                relayIntents = ArrayList(3)
            }
            val pageUri = Uri.parse(relayUri) ?: return
            relayIntents?.add(RelayIntent(pageUri))
        }

        internal fun failed(message: String) {
            navigateCallback?.invoke(NavigateResult(false, message))
        }
    }

    class RelayIntent(
            uri: Uri
    ) : RouterIntent(uri)
}

/**
 * 跳转的结果
 */
data class NavigateResult(
        val success: Boolean = true,
        val message: String = ""
)

/**
 * 携带有 Context 的类型
 *
 * @property self    原始的对象
 * @property context 携带的 Context
 */
abstract class Contextable {
    abstract val self: Any
    abstract val context: Context
}