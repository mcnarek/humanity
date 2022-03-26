package com.humanity.weatherapp.managers.places

import android.Manifest
import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.util.Log
import androidx.annotation.RequiresPermission
import com.google.android.gms.common.api.ApiException
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.model.PlaceLikelihood
import com.google.android.libraries.places.api.net.*
import com.humanity.weatherapp.domain.entity.PlaceEntity
import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject

class PlaceManagerImpl @Inject constructor(private val _client: PlacesClient) : PlaceManager {
    companion object {
        private const val TAG = "places"
    }

    @SuppressLint("MissingPermission")
    @RequiresPermission(anyOf = [Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION])
    override suspend fun getPlaceInfo(): PlaceEntity {
        val placeFields: List<Place.Field> =
            listOf(Place.Field.NAME, Place.Field.ID)
        // Use the builder to create a FindCurrentPlaceRequest.
        val placesClient: PlacesClient = _client
        val request: FindCurrentPlaceRequest = FindCurrentPlaceRequest.newInstance(placeFields)
        val placeResponse = placesClient.findCurrentPlace(request)
        return suspendCancellableCoroutine { continuation: CancellableContinuation<PlaceEntity> ->
            placeResponse.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val response = task.result
                    for (placeLikelihood: PlaceLikelihood in response?.placeLikelihoods
                        ?: emptyList()) {
                        val name = placeLikelihood.place.name
                        val id: String? = placeLikelihood.place.id
                        Log.i(TAG, "Place '$name' has likelihood: ${placeLikelihood.likelihood}")

                        if (!id.isNullOrEmpty()) {
                            getPlacePhoto(placesClient, id) {
                                continuation.resume(PlaceEntity(name, it)) {
                                    it.printStackTrace()
                                }
                            }
                            return@addOnCompleteListener
                        }
                    }
                } else {
                    val exception = task.exception
                    if (exception is ApiException) {
                        Log.e(TAG, "Place not found: ${exception.statusCode}")
                    }
                }
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun getPlacePhoto(
        placesClient: PlacesClient,
        placeId: String,
        onResult: (Bitmap?) -> Unit = {}
    ) {
        // Specify fields. Requests for photos must always have the PHOTO_METADATAS field.
        val fields = listOf(Place.Field.PHOTO_METADATAS)

        // Get a Place object (this example uses fetchPlace(), but you can also use findCurrentPlace())
        val placeRequest = FetchPlaceRequest.newInstance(placeId, fields)

        placesClient.fetchPlace(placeRequest)
            .addOnSuccessListener { response: FetchPlaceResponse ->
                val place = response.place

                // Get the photo metadata.
                val metada = place.photoMetadatas
                if (metada.isNullOrEmpty()) {
                    Log.w(TAG, "No photo metadata.")
                    return@addOnSuccessListener
                }
                val photoMetadata = metada.first()

                // Get the attribution text.
                val attributions = photoMetadata?.attributions

                // Create a FetchPhotoRequest.
                val photoRequest = FetchPhotoRequest.builder(photoMetadata).build()

                placesClient.fetchPhoto(photoRequest)
                    .addOnSuccessListener { fetchPhotoResponse: FetchPhotoResponse ->
                        val bitmap = fetchPhotoResponse.bitmap
                        onResult(bitmap)
                    }.addOnFailureListener { exception: Exception ->
                        if (exception is ApiException) {
                            val statusCode = exception.statusCode
                            Log.e(TAG, "Place not found: $statusCode - " + exception.message)
                            onResult(null)
                        }
                    }
            }
    }
}