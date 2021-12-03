package com.aungbophyoe.space.checkinternetconnectionutil

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    var snackbar: Snackbar? = null
    val parentLayout:ConstraintLayout by lazy {
        findViewById(R.id.clLayout)
    }
    val tvStatus : TextView by lazy {
        findViewById(R.id.tvStatus)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tvStatus.text = "OnCreate"
        setInternetConnectivityObserver()
    }

    override fun onResume() {
        super.onResume()
        NetworkConnectivityChecker.checkForConnection()
    }

    private fun setInternetConnectivityObserver() {
        NetworkConnectivityChecker.observe(this, liveDataObserver)
    }

    private val liveDataObserver: Observer<Boolean> = Observer { isConnected ->
        if (!isConnected) {
            tvStatus.text = "No Internet Connection"
            //Can use your own logic later -- Using snackbar as default. Build your own listener to create custom view
            parentLayout?.let {
                snackbar = Snackbar.make(it, "No Internet Connection", Snackbar.LENGTH_LONG)
                snackbar?.show()
            }
        } else {
            tvStatus.text = "Connected"
            snackbar?.dismiss()
        }
    }
}