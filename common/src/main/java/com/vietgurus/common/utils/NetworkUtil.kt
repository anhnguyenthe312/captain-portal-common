package com.vietgurus.common.utils

import android.content.Context
import android.net.ConnectivityManager

class NetworkUtil{
    companion object {
        fun isConnectedToInternet(applicationContext: Context): Boolean {
            val connectivityManager = applicationContext.getSystemService(
                    Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            return activeNetworkInfo != null && activeNetworkInfo.isConnected
        }
    }
}