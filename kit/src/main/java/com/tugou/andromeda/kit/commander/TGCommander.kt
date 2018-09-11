package com.tugou.andromeda.kit.commander

import androidx.collection.ArrayMap

class TGCommander(
        private val commandName: String,
        private val innerActionMap: TGActionMap
) {

    private var autoExecute = true

    val isPaused = !autoExecute

    val isCompleted = innerActionMap.isEmpty()

    /**
     * 开始执行队列任务
     */
    fun execute() {
        if (innerActionMap.isEmpty()) {
            sCommanderMap.remove(commandName)
            return
        }

        val action = innerActionMap.peek()
        if (action != null) {
            action.action()
        } else {
            // 如果 action 未实现, 则跳过
            innerActionMap.pop()
            execute()
        }
    }

    /**
     * 完成当前队首的任务
     */
    fun complete() {
        innerActionMap.pop()
        if (autoExecute) {
            execute()
        }
    }

    /**
     * 完成指定名称的任务
     */
    fun complete(name: String) {
        innerActionMap.remove(name)
        if (autoExecute) {
            execute()
        }
    }

    /**
     * 取消对应名称的 Action
     */
    fun cancel(actionName: String) {
        innerActionMap.remove(actionName)
    }

    /**
     * 暂停任务执行
     */
    fun pause() {
        autoExecute = false
    }

    /**
     * 继续任务执行
     */
    fun resume() {
        autoExecute = true
        execute()
    }

    /**
     * 清除所有的任务
     */
    fun clear() {
        innerActionMap.clear()
        sCommanderMap.remove(commandName)
    }

    fun enqueue(name: String) {
        innerActionMap[name] = null
    }

    fun enqueue(name: String, action: TGAction) {
        innerActionMap[name] = action
    }

    /**
     * 实现对应名称的 Action
     *
     * @param actionName
     * @param action
     */
    internal fun implement(actionName: String, action: TGAction) {
        innerActionMap[actionName] = action
    }

    companion object {

        private val sCommanderMap = ArrayMap<String, TGCommander>()

        @JvmStatic
        fun create(name: String, creator: TGActionMap.() -> Unit): TGCommander {
            val commander = TGCommander(name, TGActionMap().apply(creator))
            sCommanderMap[name] = commander
            return commander
        }

        @JvmStatic
        fun of(name: String): TGCommander {
            return sCommanderMap[name]
                    ?: throw RuntimeException(name + "还没有被创建, 务必显式调用 TGCommand.create(String) 创建对应的 Commander 实例.")
        }

        @JvmStatic
        fun of(name: String, implementor: TGCommanderImplementor.() -> Unit): TGCommander {
            val commander = sCommanderMap[name]
                    ?: throw RuntimeException(name + "还没有被创建, 务必显式调用 TGCommand.create(String) 创建对应的 Commander 实例.")
            commander.innerActionMap.putAll(TGCommanderImplementor().apply(implementor).actionMap)
            return commander
        }
    }

}
