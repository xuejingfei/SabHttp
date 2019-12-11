package com.demo.sabhttplibrary

import java.io.InputStream

public interface CallbackListener {
    abstract fun onSuccess(inputStream: InputStream?)
    abstract fun onFail()
}
