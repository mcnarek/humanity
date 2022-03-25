package com.humanity.weatherapp

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex


/**
 * Created by Narek Hayrapetyan on 25 Mar 2022.
 * Copyright: Humanity
 * E-Mail: mcnarek@gmail.com
 */
class App : Application() {
    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)

        MultiDex.install(this)
    }
}