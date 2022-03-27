package com.humanity.weatherapp.api

import android.content.Context
import android.util.Log
import com.humanity.weatherapp.BuildConfig
import com.humanity.weatherapp.api.services.WeatherService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import okio.IOException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton


/**
 * Created by Narek Hayrapetyan on 25 Mar 2022.
 * Copyright: WeatherApp
 * E-Mail: mcnarek@gmail.com
 */
@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {
    private const val TIMEOUT: Long = 60L

    @Provides
    @Singleton
    fun provideOkHttpClient(
        @Named("appInterceptor") appInterceptor: Interceptor,
        @Named("errorInterceptor") errorInterceptor: Interceptor,
         connectivityInterceptor: ConnectivityInterceptor
    ): OkHttpClient = OkHttpClient.Builder().apply {
        readTimeout(TIMEOUT, TimeUnit.SECONDS)
        writeTimeout(TIMEOUT, TimeUnit.SECONDS)
        connectTimeout(TIMEOUT, TimeUnit.SECONDS)
        addInterceptor(errorInterceptor)
        addInterceptor(connectivityInterceptor)
        addInterceptor(appInterceptor)
        if (BuildConfig.DEBUG) {
            addInterceptor(HttpLoggingInterceptor { Log.d("api", it) }
                .setLevel(HttpLoggingInterceptor.Level.BODY))
        }
        connectionSpecs(listOf(ConnectionSpec.COMPATIBLE_TLS, ConnectionSpec.CLEARTEXT))
    }.build()

    @Provides
    @Singleton
    @Named("errorInterceptor")
    fun provideErrorInterceptor(): Interceptor = Interceptor(
        @Throws(IOException::class)
        fun(chain: Interceptor.Chain): Response {
            val request: Request = chain.request()
            val response: Response = chain.proceed(request)
            return try {
                if (response.code in 400..499) {
                    Log.e("Network Error:", response.message + " | code" + response.code)
                }
                if (response.code in 500..599) {
                    Log.e("Server Error:", response.message + " | code" + response.code)
                }
                response
            } catch (e: java.io.IOException) {
                response
            }
        })

    @Provides
    @Singleton
    @Named("appInterceptor")
    fun provideAppInterceptor(): Interceptor =
        Interceptor { chain: Interceptor.Chain ->
            val request = chain.request()
            val requestBuilder = request.newBuilder()
            chain.proceed(requestBuilder.build())
        }

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
    ): Retrofit = Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Singleton
    @Provides
    fun provideConnectivityInterceptor(@ApplicationContext context: Context): ConnectivityInterceptor =
        ConnectivityInterceptor(context = context)

    @Provides
    @Singleton
    fun provideWeatherService(retrofit: Retrofit): WeatherService =
        retrofit.create(WeatherService::class.java)
}