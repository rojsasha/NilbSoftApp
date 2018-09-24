package com.example.alex.nilbsoftapp.ui.weather

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.location.*
import android.os.Bundle
import android.util.Log
import com.example.alex.nilbsoftapp.R
import com.example.alex.nilbsoftapp.data.api.WeatherApi
import com.example.alex.nilbsoftapp.data.api.entity.MainModelWeather
import com.example.alex.nilbsoftapp.data.db.AppDataBase
import com.example.alex.nilbsoftapp.data.db.entity.DBEntity
import com.example.alex.nilbsoftapp.utils.StringResources
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*


class WeatherPresenter(private val mLocationManager: LocationManager,
                       private val service: WeatherApi?,
                       private val geoCoder: Geocoder,
                       private val stringResources: StringResources,
                       private val db: AppDataBase?) : WeatherContract.Presenter {

    private var mLocation: Location? = null
    private var addresses: List<Address>? = null
    private var mView: WeatherContract.View? = null
    private var weatherText: String = ""
    private var temperature: String = ""
    private var imageURL: String = ""
    private var locationLocality: String = ""

    override fun bind(view: WeatherContract.View) {
        mView = view
    }

    override fun unbind() {
        mView = null
    }


    @SuppressLint("MissingPermission")
    override fun getLocation() {
        mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                0,
                100f,
                locationListener)
        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                0,
                100f,
                locationListener)
        Log.d(TAG, "location start")
        mView!!.showProgressBar()
    }


    private val locationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {
            mLocation = location
            if (isViewAttached())
                setLocation()
            Log.d(TAG, "location successful")
        }

        override fun onStatusChanged(s: String, i: Int, bundle: Bundle) {

        }

        @SuppressLint("MissingPermission")
        override fun onProviderEnabled(s: String) {
            val location = mLocationManager.getLastKnownLocation(s)
            mLocation = location
            if (isViewAttached())
                setLocation()
            Log.d(TAG, "location successful")
        }


        override fun onProviderDisabled(s: String) {

        }

        private fun isViewAttached(): Boolean {
            return mView != null
        }

        private fun setLocation() {

            getGeoCoderRetrieve()
            getWeather()
        }
    }

    // получение локации из geoCoder

    private fun setViewElementsContent() {
        mView!!.tvSetCoordinates(stringResources.getString(R.string.longtitude)
                + mLocation!!.longitude + "\n" +
                stringResources.getString(R.string.lantitude) + mLocation!!.latitude)

        mView!!.tvSetLocation(locationLocality)

        mView!!.setWeatherData(imageURL, weatherText, temperature)
    }

    fun getGeoCoderRetrieve() {

        addresses = geoCoder.getFromLocation(mLocation!!.latitude,
                mLocation!!.longitude, 1)

        locationLocality = addresses!![0].countryName + " " +
                addresses!![0].locality
    }

    // получение погоды из https://openweathermap.org/current

    private fun getWeather() {
        service!!.getWeather(mLocation!!.latitude.toString(),
                mLocation!!.longitude.toString(), "metric",
                stringResources.getString(R.string.API_KEY))
                .enqueue(object : Callback<MainModelWeather> {
                    override fun onFailure(call: Call<MainModelWeather>, t: Throwable) {

                        mView!!.hideProgressBar()

                    }

                    override fun onResponse(call: Call<MainModelWeather>, response: Response<MainModelWeather>) {
                        if (response.body() != null && response.isSuccessful) {
                            formatWeatherData(response.body()!!)
                        }
                        mView!!.hideProgressBar()
                    }


                })

    }

    private fun getData(): String {
        val currentDatePattern = "dd MMM yyyy HH:mm"

        val sdf = SimpleDateFormat(currentDatePattern, Locale.getDefault())

        return sdf.format(Date())
    }

    private fun saveToHistory() {
        val dbModel = DBEntity(getData(),
                coordinatesFormat(),
                locationLocality,
                weatherText, temperature)
        db!!.dbDAO().saveToHistory(dbModel)
    }

    private fun formatWeatherData(body: MainModelWeather) {
        weatherText = body.weather[0].description
        temperature = body.main.temp.toString()
        imageURL = stringResources.getString(R.string.image_url) + body.weather[0].icon + ".png"
        setViewElementsContent()
        saveToHistory()
    }

    private fun coordinatesFormat(): String {
        return String.format("%.5f", mLocation!!.latitude) + "\n" +
                String.format("%.5f", mLocation!!.longitude)
    }

}