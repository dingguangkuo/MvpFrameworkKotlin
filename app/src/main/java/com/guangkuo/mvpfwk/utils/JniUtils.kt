package com.guangkuo.mvpfwk.utils

class JniUtils {

    external fun loadCString(): String

    companion object {
        init {
            System.loadLibrary("GkLibrary")
        }
    }
}
