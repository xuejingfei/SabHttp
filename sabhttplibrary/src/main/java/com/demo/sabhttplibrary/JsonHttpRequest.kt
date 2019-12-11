package com.demo.sabhttplibrary

import java.io.BufferedOutputStream
import java.io.OutputStream
import java.net.HttpURLConnection
import java.net.URL

/**
 * Author：xuejingfei
 *
 * Description：json形式的request
 *
 * Date：2019-07-27 15:36
 */
class JsonHttpRequest:IHttpRequest {
    private var mUrl:String ?= null
    private var mData:ByteArray ?=null
    private var mCallbackListener:CallbackListener ?=null
    private var mUrlConnection:HttpURLConnection ?=null

    override fun setUrl(url: String) {
        mUrl = url
    }

    override fun setData(data: ByteArray) {
        mData = data
    }

    override fun setListener(callbackListener: CallbackListener) {
        mCallbackListener = callbackListener
    }

    override fun execute() {
        //执行具体的网络访问操作
        var url:URL ?= null
        try {
            url = URL(this.mUrl)
            mUrlConnection = url.openConnection() as HttpURLConnection?
            mUrlConnection?.connectTimeout = 6000
            mUrlConnection?.useCaches = false
            mUrlConnection?.instanceFollowRedirects = true
            mUrlConnection?.readTimeout = 3000
            mUrlConnection?.doInput = true
            mUrlConnection?.doOutput =true
            mUrlConnection?.requestMethod = "POST"
            mUrlConnection?.setRequestProperty("Content-type","application/json;charset=UTF-8")
            mUrlConnection?.connect()
            //使用字节流发送数据
            val out: OutputStream? = mUrlConnection?.outputStream
            //缓冲字节流包装字节流
            val bos = BufferedOutputStream(out)
            //把这个字节数组的数据写入缓冲区
            bos.write(mData)
            //刷新缓冲区，发送数据
            bos.flush()
            out?.close()
            bos.close()
            //字符流写入数据
            if(mUrlConnection?.responseCode == HttpURLConnection.HTTP_OK) {
                mCallbackListener?.onSuccess(mUrlConnection?.inputStream)
            } else{
                throw Exception("失败")
            }
        }catch (e:Exception) {
            throw Exception("失败")
        }finally {
            mUrlConnection?.disconnect()
        }
    }
}
