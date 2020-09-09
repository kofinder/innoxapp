package com.finderbar.innox.services
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.finderbar.innox.utilities.NetworkUtil

class NetworkChangeReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
       // connectivityReceiverListener?.updateNetworkStatus(NetworkUtil.getConnectivityStatusString(context))
    }
    interface NetworkListener {
        fun updateNetworkStatus(result: Int)
    }

    companion object {
        var connectivityReceiverListener: NetworkListener? = null
    }
}
