package com.demo.sabhttplibrary

/**
 * Author：xuejingfei
 *
 * Description：
 *
 * Date：2019-07-27 13:50
 */
public interface IHttpRequest {
    abstract fun setUrl(Url: String)
    abstract fun setData(data: ByteArray)
    abstract fun setListener(callbackListener: CallbackListener)
    abstract fun execute()
}