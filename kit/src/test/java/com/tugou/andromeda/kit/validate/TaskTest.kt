package com.tugou.andromeda.kit.validate

import org.junit.Test

class TaskTest {

    @Test
    fun test() {
//        val queue = TaskManager.getTaskQueue("test")
//        queue.enqueue(Task("A", 2))
//        queue.enqueue(Task("B", 4))
//        queue.enqueue(Task("C", 2))
//        queue.enqueue(Task("D", 3))
//        queue.enqueue(Task("E", 5))
//
//        for (task in queue.taskSet) {
//            System.out.println(task.name + task.priority)
//        }


        TGInbox.observe<Boolean>("article_collect_123") {
            if (it) {
                System.out.println("收藏了")
            } else {
                System.out.println("取消收藏了")
            }
        }

        TGInbox.observe<Boolean>("article_collect_1223") {
            if (it) {
                System.out.println("收藏了")
            } else {
                System.out.println("取消收藏了")
            }
        }

        TGInbox.notify("article_collect_23", true)

        while (true){
        }
    }

}