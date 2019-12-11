package com.demo.sabhttplibrary

/**
 * Author：xuejingfei
 *
 * Description：
 *
 * Date：2019-07-27 16:19
 */
interface IJsonDataListener<T> {
    fun onSuccess(data: T)
    fun onFail()
}