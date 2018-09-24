package com.example.alex.nilbsoftapp.ui.weather

import com.example.alex.nilbsoftapp.LifeCicle


interface WeatherContract {
    interface View {
        fun tvSetCoordinates(coordinates: String)
        fun tvSetLocation(location: String)
        fun setWeatherData(img: String, weatherText: String, temperature: String)
        fun showProgressBar()
        fun hideProgressBar()
    }

    interface Presenter : LifeCicle<View> {
        fun getLocation()
    }
}