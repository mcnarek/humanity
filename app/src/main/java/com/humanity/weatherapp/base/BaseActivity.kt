package com.humanity.weatherapp.base

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.datatransport.runtime.retries.Retries.retry
import com.google.android.material.snackbar.Snackbar
import com.humanity.weatherapp.R
import com.humanity.weatherapp.base.extensions.isConnectedNetwork
import dagger.hilt.EntryPoint
import dagger.hilt.android.AndroidEntryPoint


/**
 * Created by Narek Hayrapetyan on 26 Mar 2022.
 * Copyright: WeatherApp
 * E-Mail: mcnarek@gmail.com
 *
 */
abstract class BaseActivity : AppCompatActivity() {
    private var snackBar: Snackbar? = null

    private val connectivityLiveData: NetworkStatusLiveData by lazy {
        NetworkStatusLiveData(this)
    }

    override fun onStart() {
        super.onStart()

        connectivityLiveData.observe(this) { isConnectedNetwork ->
            if (!isConnectedNetwork) {
                showSnackBar(getString(R.string.no_internet), getString(R.string.retry))
            } else {
                snackBar?.dismiss()
            }
        }
    }

    /**
     * Show's snack bar message
     *
     * @param text as message on snack bar
     * @param actionName as action link button label
     */
    protected fun showSnackBar(text: String?, actionName: String?) {
        val rootView: View = window.decorView.findViewById(android.R.id.content)
        text?.let {
            snackBar = Snackbar.make(rootView, it, Snackbar.LENGTH_LONG).apply {
                if (actionName != null) {
                    setAction(actionName) {
                        if (context.isConnectedNetwork) {
                            dismiss()
                        } else {
                            showSnackBar(text, actionName)
                        }
                    }
                }
            }
            snackBar?.show()
        }
    }

    override fun onStop() {
        super.onStop()

        connectivityLiveData.removeObservers(this)
    }

    override fun onDestroy() {
        snackBar = null

        super.onDestroy()
    }
}