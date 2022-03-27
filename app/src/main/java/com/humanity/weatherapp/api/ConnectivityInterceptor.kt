package com.humanity.weatherapp.api

import android.content.Context
import android.net.ConnectivityManager
import com.humanity.weatherapp.R
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Interceptor
import okhttp3.Protocol
import okhttp3.Request
import okhttp3.Response
import okhttp3.internal.EMPTY_RESPONSE
import java.io.IOException
import javax.inject.Inject


/**
 * Created by Narek Hayrapetyan on 27 Mar 2022.
 * Copyright: WeatherApp
 * E-Mail: mcnarek@gmail.com
 */
class ConnectivityInterceptor @Inject constructor(@ApplicationContext private val context: Context) :
    Interceptor {

    companion object {
        const val NO_INTERNET = 503
    }

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        return if (!isConnected) {
            return Response.Builder()
                .request(chain.request())
                .protocol(Protocol.HTTP_1_1)
                .body(EMPTY_RESPONSE)
                .code(NO_INTERNET)
                .message(context.getString(R.string.no_internet))
                .build()
            // Throwing our custom exception 'NoConnectivityException'
        } else {
            val builder: Request.Builder = chain.request().newBuilder()
            chain.proceed(builder.build())
        }
    }

    private val isConnected: Boolean
        get() {
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val netInfo = connectivityManager.activeNetworkInfo
            return netInfo != null && netInfo.isConnected
        }
}