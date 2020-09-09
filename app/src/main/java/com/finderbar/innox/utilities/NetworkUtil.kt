package com.finderbar.innox.utilities
import android.content.Context
import android.net.ConnectivityManager
import android.os.Build
import android.net.NetworkCapabilities
import com.finderbar.innox.AppContext


object NetworkUtil {
    fun isAvailable(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
           true
        } else {
            val cm = AppContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = cm.activeNetworkInfo
            networkInfo.isConnected && networkInfo.isAvailable
        }
    }
}