package com.guangkuo.mvpfwk.utils

import android.Manifest.permission.ACCESS_NETWORK_STATE
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.support.annotation.RequiresPermission
import com.guangkuo.mvpfwk.app.App

/**
 * <pre>
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2016/08/02
 * desc  : utils about network
</pre> *
 */
class NetworkUtils private constructor() {
    init {
        throw UnsupportedOperationException("u can't instantiate me...")
    }

    companion object {
        /**
         * Return whether network is connected.
         *
         * Must hold
         * `<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />`
         *
         * @return `true`: connected<br></br>`false`: disconnected
         */
        @RequiresPermission(ACCESS_NETWORK_STATE)
        fun isConnected(): Boolean {
            val info = getActiveNetworkInfo()
            return info != null && info.isConnected
        }

        @RequiresPermission(ACCESS_NETWORK_STATE)
        private fun getActiveNetworkInfo(): NetworkInfo? {
            val manager = App.instance.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            return manager.activeNetworkInfo
        }
    }
}
