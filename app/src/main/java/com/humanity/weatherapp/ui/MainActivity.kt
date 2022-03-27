package com.humanity.weatherapp.ui

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.ColorInt
import androidx.annotation.RequiresPermission
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import coil.load
import com.humanity.weatherapp.R
import com.humanity.weatherapp.base.BaseActivity
import com.humanity.weatherapp.base.extensions.colorInt
import com.humanity.weatherapp.base.extensions.isConnectedNetwork
import com.humanity.weatherapp.base.extensions.now
import com.humanity.weatherapp.base.extensions.setStatusBarColor
import com.humanity.weatherapp.databinding.ActivityMainBinding
import com.humanity.weatherapp.databinding.LayoutNoInternetBinding
import com.humanity.weatherapp.domain.entity.PlaceEntity
import com.humanity.weatherapp.domain.entity.WeatherEntity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity() {
    /**
     * Location permission launcher
     */
    private val requestLocationPermissionLauncher: ActivityResultLauncher<Array<String>> by lazy {
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            locationPermissionHasGiven = when {
                permissions.getOrElse(ACCESS_FINE_LOCATION) { false } -> true
                permissions.getOrElse(ACCESS_COARSE_LOCATION) { false } -> true

                else -> {
                    showPermissionAlertDialog()
                    false
                }
            }
            @SuppressLint("MissingPermission")
            if (locationPermissionHasGiven) {
                startWeatherUpdates()
            }
        }
    }

    private var locationPermissionHasGiven: Boolean = false

    private val weatherViewModel: WeatherViewModel by viewModels()

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = null

        requestLocationUpdates()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.settings, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.settings -> {
                FragmentDialogSettings.show(supportFragmentManager) {
                    weatherViewModel.changeUnits(it)
                }
            }
        }

        return super.onOptionsItemSelected(item)
    }

    /**
     * Request for location updates, first will ask for location permission if not granted before
     */
    private fun requestLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(
                this,
                ACCESS_FINE_LOCATION
            ) != PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                ACCESS_COARSE_LOCATION
            ) != PERMISSION_GRANTED
        ) {
            val shouldAsk = ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                ACCESS_FINE_LOCATION
            ) && ActivityCompat.shouldShowRequestPermissionRationale(this, ACCESS_COARSE_LOCATION)

            if (shouldAsk) {
                showPermissionAlertDialog()
            } else {
                askForLocationPermissions()
            }

            locationPermissionHasGiven = false
            return
        }

        locationPermissionHasGiven = true

        startWeatherUpdates()
    }

    private fun askForLocationPermissions() {
        requestLocationPermissionLauncher.launch(
            arrayOf(ACCESS_COARSE_LOCATION, ACCESS_FINE_LOCATION)
        )
    }

    @RequiresPermission(anyOf = [ACCESS_COARSE_LOCATION, ACCESS_FINE_LOCATION])
    private fun startWeatherUpdates() {
        subscribeToViewUpdates()

        weatherViewModel.startLocationUpdates()
    }

    private fun subscribeToViewUpdates() {
        binding.swipeRefresh.setOnRefreshListener {
            weatherViewModel.refresh()
        }

        weatherViewModel.place.observe(this) {
            updatePlace(it)
        }

        weatherViewModel.weather.observe(this) {
            updateWeatherView(it)
        }

        weatherViewModel.error.observe(this) {
            binding.swipeRefresh.isRefreshing = false
            showSnackBar(it?.message, null)
            if (it?.message.equals(getString(R.string.no_internet))) {
                showNoInternet(true)
            }
        }
    }

    private fun updateWeatherView(it: WeatherEntity) {
        binding.apply {
            swipeRefresh.isRefreshing = false
            weatherImageIcon.load(it.iconUrl)
            textDate.text = now()
            textCity.text = it.name
            it.animation?.let { anim ->
                lottie.cancelAnimation()
                lottie.setAnimation(anim)
                lottie.playAnimation()
            }
            @ColorInt val colorOfBg = colorInt(it.weatherColor)
            overlay.setBackgroundColor(colorOfBg)
            this@MainActivity.setStatusBarColor(colorOfBg)
            textDescription.text = it.description
            textTemperature.text = getString(
                R.string.temperature,
                it.weatherTemperature,
                it.units.symbol
            )

            textMinMax.text = getString(
                R.string.temperatureMinMax,
                it.minTemperature,
                it.units.symbol,
                it.maxTemperature,
                it.units.symbol
            )
        }
    }

    private fun updatePlace(it: PlaceEntity) {
        binding.apply {
            swipeRefresh.isRefreshing = false
            imageCity.load(it.placePhoto)
        }
    }

    /**
     * Shows dialog to user explain about why user should allow access location permission
     */
    private fun showPermissionAlertDialog() {
        AlertDialog.Builder(this).apply {
            setMessage(getString(R.string.location_permission_message))
            setCancelable(true)

            setPositiveButton(
                getString(R.string.settings)
            ) { dialog, _ ->
                dialog.cancel()

                openPermissionSettings()
            }

            setNegativeButton(
                getString(android.R.string.cancel)
            ) { dialog, _ -> dialog.cancel() }
        }.create().show()
    }

    /**
     * Go to permission settings page in android OS
     */
    private fun openPermissionSettings() {
        startActivity(
            Intent(
                Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                Uri.parse("package:${this.packageName}")
            )
        )
    }

    override fun showCustomNoInternet() {
        showNoInternet(true)
    }

    override fun hideCustomNoInternet() {
        showNoInternet(false)
    }

    private fun showNoInternet(show: Boolean) {
        binding.stubNoInternet.apply {
            if (show) {
                val inflatedView: View? = inflate()
                val binding: LayoutNoInternetBinding? = inflatedView?.let {
                    LayoutNoInternetBinding.bind(it)
                }
                binding?.retryButton?.setOnClickListener {
                    if (context.isConnectedNetwork) {
                        isVisible = false
                        weatherViewModel.refresh()
                    } else {
                        setVisible(true)
                    }
                }
            } else {
                setVisible(false)
            }
        }
    }
}