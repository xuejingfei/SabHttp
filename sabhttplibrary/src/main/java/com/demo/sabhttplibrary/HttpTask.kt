package com.demo.sabhttplibrary

import com.alibaba.fastjson.JSON
import java.util.concurrent.Delayed
import java.util.concurrent.TimeUnit

/**
 * Author：xuejingfei
 *
 * Description：
 *
 * Date：2019-07-27 14:25
 */
public  class  HttpTask<T:Any?>(url :String,requestData:T,iHttpRequest: IHttpRequest,callbackListener: CallbackListener) : Runnable ,Delayed{
    private var mIHttpRequest:IHttpRequest ?= null
    var retryCount = 0
    private var delayTime = 0L

    init {
        iHttpRequest.setUrl(url)
        if(requestData!=null) {
            val content:String  = JSON.toJSON(requestData) as String
            iHttpRequest.setData(content.toByteArray(Charsets.UTF_8))
        }
        iHttpRequest.setListener(callbackListener)
        mIHttpRequest = iHttpRequest
    }

    fun setDelayTime(delayedTime:Long) {
        delayTime = System.currentTimeMillis() + delayedTime
    }

    override fun compareTo(other: Delayed?): Int {
        return 0
    }

    override fun getDelay(p0: TimeUnit): Long {
        return p0.convert(this.delayTime -System.currentTimeMillis(),TimeUnit.MILLISECONDS)
    }


    override fun run() {
        try {
            mIHttpRequest?.execute()
        } catch (e: Exception) {
            TaskPoolManager.instance.addDelayTask(this)
        }
    }

}