package com.example.alex.nilbsoftapp

import android.app.Application
import android.content.Context
import com.example.alex.nilbsoftapp.data.api.WeatherApi
import com.example.alex.nilbsoftapp.data.api.WeatherService
import com.example.alex.nilbsoftapp.data.db.AppDataBase

class StartApplication : Application() {
    private var mService: WeatherApi? = null
    private var mDB: AppDataBase? = null

    override fun onCreate() {
        super.onCreate()
        mService = WeatherService.initRetrofit()
        mDB = AppDataBase.getInstance(this)

    }

    companion object {
        fun get(context: Context): StartApplication {
            return context.applicationContext as StartApplication
        }
    }

    fun getService(): WeatherApi? {
        return mService
    }

    fun getDB(): AppDataBase? {
        return mDB
    }

}