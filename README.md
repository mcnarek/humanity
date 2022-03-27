Weather demo app

# Weather App

![icon](https://user-images.githubusercontent.com/5946745/160274616-ac52442b-b83b-4aa6-b1d7-e1ddb298ce18.png) #

Weather is an application for Android, to show current location weather

![photo_2022-03-27_13-04-18 (2)](https://user-images.githubusercontent.com/5946745/160275180-38e84668-bb63-4618-8f16-7fb3e3a59d0a.jpg)

![photo_2022-03-27_13-04-18](https://user-images.githubusercontent.com/5946745/160275181-7869a5ea-3729-4da9-98a3-3b9102d8b7c7.jpg)


User can see weather temperature for current location
 - refresh anytime by swiping from top to bottom
 - change measurement units as Celsius, Fahrenheight or Kelvin by tapping on the top left gear icon

App will show nearby image in city as background

## Documentation ##

Developed with Love using:

- Language - Kotlin
- Android SDK version - 31
- Build tools - 31.0.0
- Min android support version - 21
- Gradle - 7.1.2
- Gradle plugin - 7.4.1
- Architecture - MVVM
- UI - ViewBidning
- API openweathermap.org - [developer guide]: https://openweathermap.org/api

## Used Libraries ##
- Kotlin core and coroutines (org.jetbrains.kotlinx:kotlinx-coroutines-core) // for async calls
- Coroutine Android - (org.jetbrains.kotlinx:kotlinx-coroutines-android)
- appCompat older version compatibilities - (androidx.appcompat:appcompat)
- lifecycle extensions - (androidx.lifecycle:lifecycle-extensions)
- material lib - (com.google.android.material:material)
- constraintlayout for complex views (androidx.constraintlayout:constraintlayout)
- swipe to refresh for refreshing weather (androidx.swiperefreshlayout:swiperefreshlayout)
- retrofit - for rest api call handle (com.squareup.retrofit2:retrofit)
- okhhttp -  for netork calls (com.squareup.okhttp3:okhttp)
- GSON - for parsing models from json format (com.google.code.gson:gson)
- Dagger Hilt - for dependency injection - (com.google.dagger:hilt-android)
- Coil - image load and display from network (io.coil-kt:coil)
- Google location gms library for getting location (com.google.android.gms:play-services-location)
- Google places - for getting image of currrent location (com.google.android.libraries.places:places)
- jUnit, mockito, espresso  - for testing
- Lottie - for animations (com.airbnb.android:lottie)
- Crashlytics - getting crash reports (com.google.firebase:firebase-crashlytics-ktx)
- Analytics - getting analytics data with crashes (com.google.firebase:firebase-analytics-ktx)

## Internal Libraries ##

 - Base - base functions, classes, extension functions
   
 - Rest - res api calls based on okhttp and retrofit 
   [developer guide]: (https://developers.google.com/android/reference/com/google/android/gms/location/package-summary)
 
- Location - location manager library to detect and get location updates
   [developer guide]: (https://developers.google.com/android/reference/com/google/android/gms/location/package-summary)

 - Places - The Places SDK for Android allows you to build location-aware apps that respond contextually to the local businesses and other places near the user's device.
   [developer guide]: (https://developers.google.com/maps/documentation/places/android-sdk/overview)


#### Using Android Studio ####

To run the app using Android Studio, simply open the project in the root directory of the
repository.

#### About author ####

Narek Hayrapetyan - Android software engineer
