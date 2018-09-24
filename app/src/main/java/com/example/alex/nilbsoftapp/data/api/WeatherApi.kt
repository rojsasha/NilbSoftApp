package com.example.alex.nilbsoftapp.data.api

import com.example.alex.nilbsoftapp.data.api.entity.MainModelWeather
import com.example.alex.nilbsoftapp.utils.AppConstants
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET(AppConstants.ENDPOINT)
    fun getWeather(@Query("lat") lat: String,
                   @Query("lon") lon: String,
                   @Query("units") units: String,
                   @Query("appid")appid: String): Call<MainModelWeather>
}