package com.humanity.weatherapp.di

import com.google.android.libraries.places.api.net.PlacesClient
import com.humanity.weatherapp.domain.repository.PlaceRepository
import com.humanity.weatherapp.domain.repository.PlaceRepositoryImpl
import com.humanity.weatherapp.domain.repository.WeatherRepository
import com.humanity.weatherapp.domain.repository.WeatherRepositoryImpl
import com.humanity.weatherapp.domain.usecase.WeatherUseCase
import com.humanity.weatherapp.domain.usecase.WeatherUseCaseImpl
import com.humanity.weatherapp.managers.places.PlaceManager
import com.humanity.weatherapp.managers.places.PlaceManagerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped


/**
 * Created by Narek Hayrapetyan on 25 Mar 2022.
 * Copyright: WeatherApp
 * E-Mail: mcnarek@gmail.com
 */
@Module
@InstallIn(ViewModelComponent::class)
abstract class WeatherModule {
    @Binds
    @ViewModelScoped
    abstract fun providePlaceRepository(impl: PlaceRepositoryImpl): PlaceRepository

    @Binds
    @ViewModelScoped
    abstract fun provideWeatherRepository(impl: WeatherRepositoryImpl): WeatherRepository

    @Binds
    @ViewModelScoped
    abstract fun provideWeatherUseCase(impl: WeatherUseCaseImpl): WeatherUseCase

    @Binds
    @ViewModelScoped
    abstract fun providePlaceManager(impl: PlaceManagerImpl): PlaceManager
}