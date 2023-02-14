package com.matheusxreis.moviedroid.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.os.Build
import androidx.annotation.RequiresApi
import kotlinx.coroutines.flow.MutableStateFlow

class NetworkListener private constructor(private val context: Context):ConnectivityManager.NetworkCallback() {

    companion object {
        private var INSTANCE:NetworkListener? = null

        fun getInstance(context: Context): NetworkListener{
            if(INSTANCE == null){
                INSTANCE = NetworkListener(context)
            }
            return INSTANCE as NetworkListener
        }
    }


    private val isNetworkAvailable = MutableStateFlow(false)

    fun checkNetworkavailability():MutableStateFlow<Boolean> {
        val connectivityManager = context.getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager

        connectivityManager.registerDefaultNetworkCallback(this)

        var isConnected = false

        connectivityManager.allNetworks.forEach {
            network ->
            val networkCapability = connectivityManager.getNetworkCapabilities(network)
            networkCapability.let {
                if(it!=null){
                    if(it.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)){
                        isConnected = true
                        return@forEach
                    }
                }
            }
        }

        isNetworkAvailable.value = isConnected
        return isNetworkAvailable
    }

    override fun onAvailable(network: Network) {
        isNetworkAvailable.value = true
    }
    override  fun onLost(network: Network){
        isNetworkAvailable.value = false
    }
}