package com.demo.sabhttplibrary

/**
 * Author：xuejingfei
 *
 * Description：
 *
 * Date：2019-07-27 16:25
 */
class SabHttp {
    companion object {
        @JvmStatic
        fun <T,M>sendJsonRequest (url:String, requestData:M, iJsonDataListener: IJsonDataListener<T>) {
            val jsonHttpRequest = JsonHttpRequest()
            val callbackListener = JsonCallBackListener(iJsonDataListener)
            val httpTask = HttpTask(url,requestData,jsonHttpRequest,callbackListener)
            TaskPoolManager.instance.addTask(httpTask)
        }
    }

}