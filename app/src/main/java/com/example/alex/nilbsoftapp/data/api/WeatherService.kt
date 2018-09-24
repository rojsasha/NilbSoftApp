package com.example.alex.nilbsoftapp.data.api

import com.example.alex.nilbsoftapp.BuildConfig.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WeatherService {
    companion object {
        private var service: WeatherApi? = null

        fun initRetrofit(): WeatherApi? {
            if (service == null) {
                return Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
                        .create(WeatherApi::class.java)
            }
            return service
        }
    }

}