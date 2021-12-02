package com.aungbophyoe.space.checkinternetconnectionutil

import android.app.Application

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        initNetworkConnectivityChecker()
    }

    private fun initNetworkConnectivityChecker() {
        NetworkConnectivityChecker.init(this.applicationContext)
    }
}