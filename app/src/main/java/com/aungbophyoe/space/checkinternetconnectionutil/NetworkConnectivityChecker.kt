package com.aungbophyoe.space.checkinternetconnectionutil

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.util.Log
import com.aungbophyoe.space.checkinternetconnectionutil.util.NetworkConnectivityUtil
import com.aungbophyoe.space.checkinternetconnectionutil.util.SingleLiveEvent
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@AndroidEntryPoint
object NetworkConnectivityChecker: SingleLiveEvent<Boolean>() {
    @Inject lateinit var networkConnectivityUtil: NetworkConnectivityUtil
    @Inject lateinit var connectivityManager: ConnectivityManager
    override fun onActive() {
        registerCallback()
        Log.d("DG", "Network Connectivity Registered")
        super.onActive()
    }

    override fun onInactive() {
        removeCallback()
        Log.d("DG", "Network Connectivity Unregistered")
        super.onInactive()
    }

    fun checkForConnection() {
        value = networkConnectivityUtil.isConnected()
    }

    fun hasConnection():Boolean = networkConnectivityUtil.isConnected()

    /*fun init(context: Context) {
        networkConnectivityUtil = NetworkConnectivityUtil(context)
        connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }*/

    private fun notifyObservers(connectionStatus: Boolean) {
        postValue(connectionStatus)
    }

    private fun registerCallback() {
        val networkRequest = NetworkRequest.Builder()
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
            .build()
        connectivityManager.registerNetworkCallback(networkRequest, networkCallback)
    }

    private fun removeCallback() {
        connectivityManager.unregisterNetworkCallback(networkCallback)
    }

    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            notifyObservers(true)
            super.onAvailable(network)
        }

        override fun onLost(network: Network) {
            notifyObservers(false)
            super.onLost(network)
        }
    }
}