package com.humanity.weatherapp.base.extensions

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Build
import android.view.View
import android.view.WindowInsetsController
import android.view.WindowManager
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat


/**
 * Created by Narek Hayrapetyan on 26 Mar 2022.
 * Copyright: WeatherApp
 * E-Mail: mcnarek@gmail.com
 */

/**
 * Util class for check internet connection status
 * Check if there is any connectivity
 *
 * @return TRUE if connected otherwise FALSE
 */
val Context?.isConnectedNetwork: Boolean
    get() {
        if (this == null) return false

        val info: NetworkInfo? = networkInfo
        return info != null && info.isConnected
    }

/**
 * Get the network info
 *
 * @return ActiveNetworkInfo | Null
 */
private val Context.networkInfo: NetworkInfo?
    get() {
        val cm: ConnectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return cm.activeNetworkInfo
    }

@ColorInt
fun Context.colorInt(@ColorRes colorRes: Int) = ContextCompat.getColor(this, colorRes)


/**
 * Makes status bar like or dark
 *
 * @param colorRes color of status bar
 */
fun Activity?.setStatusBarColor(@ColorInt color: Int) {
    this?.window?.run {
        addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        statusBarColor = color
        clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val appearanceLightStatusBars = WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
            insetsController?.setSystemBarsAppearance(
                appearanceLightStatusBars,
                appearanceLightStatusBars
            )
        } else {
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
        }
    }
}
