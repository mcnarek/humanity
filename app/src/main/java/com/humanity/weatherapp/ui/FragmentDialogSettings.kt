package com.humanity.weatherapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.humanity.weatherapp.databinding.FragmentDialogSettingsBinding
import com.humanity.weatherapp.domain.enums.WeatherUnits


/**
 * Created by Narek Hayrapetyan on 26 Mar 2022.
 * Copyright: WeatherApp
 * E-Mail: mcnarek@gmail.com
 */
class FragmentDialogSettings : BottomSheetDialogFragment() {
    companion object {
        fun show(
            fragmentManager: FragmentManager,
            callBack: (WeatherUnits) -> Unit = {}
        ) {
            FragmentDialogSettings().apply {
                onClick = callBack
            }.show(
                fragmentManager,
                BottomSheetDialogFragment::class.java.name
            )
        }
    }

    private lateinit var binding: FragmentDialogSettingsBinding
    private var onClick: (WeatherUnits) -> Unit = {}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        return FragmentDialogSettingsBinding.inflate(inflater, container, false).apply {
            binding = this
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            buttonCelsius.setOnClickListener { click(WeatherUnits.METRIC) }
            buttonFahrenheit.setOnClickListener { click(WeatherUnits.IMPERIAL) }
            buttonKelvin.setOnClickListener { click(WeatherUnits.STANDARD) }
        }
    }

    private fun click(unit: WeatherUnits) {
        dismiss()
        onClick(unit)
    }
}