package com.demo.sabhttplibrary

import android.os.Handler
import android.os.Looper
import com.alibaba.fastjson.JSON
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.lang.reflect.ParameterizedType

/**
 * Author：xuejingfei
 *
 * Description：
 *
 * Date：2019-07-27 16:00
 */
@Suppress("UNCHECKED_CAST")
class JsonCallBackListener<T>(iJsonDataListener: IJsonDataListener<T>) :CallbackListener {
    var mHandler: Handler = Handler(Looper.getMainLooper())
    var mJsonCallBackListener:IJsonDataListener<T> ?=null
    init {
        mJsonCallBackListener = iJsonDataListener
    }
    override fun onSuccess(inputStream: InputStream?) {
        val response:String  = getContent(inputStream)
        val mResponseClass = analysisClassInfo(this)
        val classz = JSON.parseObject(response,mResponseClass) as T
        mHandler.post(Runnable {
            mJsonCallBackListener?.onSuccess(classz)
        })
    }

    private fun analysisClassInfo(obj :Any): Class<*> {
        val genType = obj.javaClass.genericSuperclass as ParameterizedType
        val type = genType.actualTypeArguments
        return type[0] as Class<*>
    }

    override fun onFail() {

    }


    private fun getContent(inputStream:InputStream?) :String {
        val content:String ?= null
            val reader = BufferedReader(InputStreamReader(inputStream))
            val sb = StringBuilder()
            var line:String ?= null
            try {
                while (true) {
                    line = reader.readLine() ?: break
                    sb.append(line + "\n")
                }
            }catch (e:IOException) {
                System.out.println("Error=$e")
            }finally {
                try {
                    inputStream?.close()
                }catch (e:IOException) {
                    System.out.println("Error=$e")
                }
            }
        return sb.toString()
    }
}