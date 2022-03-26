package com.humanity.weatherapp.base

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import androidx.lifecycle.LiveData


/**
 * Created by Narek Hayrapetyan on 26 Mar 2022.
 * Copyright: WeatherApp
 * E-Mail: mcnarek@gmail.com
 *
 * App internet connectivity manager class
 * handles application connected or disconnected from wifi or cellular network
 */
@Suppress("DEPRECATION")
class NetworkStatusLiveData(private val context: Context) :
    LiveData<Boolean>() {
    private val connectivityChangeReceiver: ConnectivityChangeReceiver by lazy {
        ConnectivityChangeReceiver {
            postValue(it)
        }
    }

    override fun onInactive() {
        super.onInactive()

        context.unregisterReceiver(connectivityChangeReceiver)
    }

    override fun onActive() {
        super.onActive()

        context.registerReceiver(connectivityChangeReceiver,
            IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
    }

    /**
     * Detector and receiver for internet connection availability.
     *
     * @param _connectivityChangeFunc callback function to get notified
     */
    @Suppress("DEPRECATION")
    private class ConnectivityChangeReceiver(private val _connectivityChangeFunc: (isConnected: Boolean) -> Unit) :
        BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val action: String? = intent.action
            if (action != null && action == ConnectivityManager.CONNECTIVITY_ACTION) {
                val isConnected: Boolean =
                    isConnectedToWifi(context) || isConnectedToMobileData(context)
                _connectivityChangeFunc.invoke(isConnected)
            }
        }

        private fun isConnectedToWifi(context: Context): Boolean {
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
            return activeNetInfo != null && activeNetInfo.isConnectedOrConnecting
        }

        private fun isConnectedToMobileData(context: Context): Boolean {
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
            return activeNetInfo != null && activeNetInfo.isConnectedOrConnecting
        }
    }
}