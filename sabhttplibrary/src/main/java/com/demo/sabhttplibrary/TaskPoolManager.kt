package com.demo.sabhttplibrary;

import android.util.Log
import java.util.concurrent.*

/**
 * Author：xuejingfei
 *
 * Description：网络连接管理
 * 1.并发接收多个请求，
 * 2.重试机制
 *
 * Date：2019-07-27 13:25
 */
public class TaskPoolManager {
    //线程池
    private var mThreadPoolExecutor: ThreadPoolExecutor? = null
    //任务队列
    private var mTaskQueue: LinkedBlockingDeque<Runnable>? = null
    //重试任务队列
    private var mDelayQueue: DelayQueue<HttpTask<*>>? = null;
    //创建核心线程，不停的去队列中获取请求，并提交给线程池处理
    private var coreThread = Runnable {
        var conn: Runnable? = null
        while (true) {
            try {
                conn = mTaskQueue?.take()
                mThreadPoolExecutor?.execute(conn)
            } catch (e: InterruptedException) {
                e.printStackTrace();
            }
        }
    }

    private var delayThread = Runnable {
        var conn: HttpTask<*> ? = null
        while (true) {
            try {
                conn = mDelayQueue?.take()
                if(conn?.retryCount!! <3) {
                    mThreadPoolExecutor?.execute(conn)
                    conn.retryCount++
                    Log.d("==重试机制==",conn.retryCount.toString() +  "  " + System.currentTimeMillis())
                } else {
                    Log.d("==重试机制==","总是失败")
                }

            } catch (e: InterruptedException) {
                e.printStackTrace();
            }
        }
    }



    //添加任务队列
    public fun addTask(task: Runnable) {
        try {
            mTaskQueue?.put(task);
        } catch (e: InterruptedException) {
            e.printStackTrace();
        }
    }

    //添加到延迟队列中
    fun addDelayTask(task: HttpTask<*>) {
        task.setDelayTime(3000)
        mDelayQueue?.offer(task)
    }

    init {
        mThreadPoolExecutor = ThreadPoolExecutor(3, 5, 15, TimeUnit.SECONDS, ArrayBlockingQueue<Runnable>(5), object : RejectedExecutionHandler {
            override fun rejectedExecution(r: Runnable, executor: ThreadPoolExecutor?) {
                addTask(r)
            }
        })
        mTaskQueue = LinkedBlockingDeque<Runnable>()
        mDelayQueue = DelayQueue()
        mThreadPoolExecutor?.execute(coreThread)
        mThreadPoolExecutor?.execute(delayThread)
    }

    companion object {
        val instance:TaskPoolManager by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            TaskPoolManager() }
    }

}
