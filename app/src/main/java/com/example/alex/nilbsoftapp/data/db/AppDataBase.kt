package com.example.alex.nilbsoftapp.data.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.example.alex.nilbsoftapp.data.db.entity.DBEntity

@Database(entities = [DBEntity::class], version = 1, exportSchema = false)
abstract class AppDataBase : RoomDatabase() {
    abstract fun dbDAO(): DatabaseDAO


    companion object {
        private var INSTANCE: AppDataBase? = null

        fun getInstance(context: Context): AppDataBase? {
            if (INSTANCE == null) {
                return Room.databaseBuilder(context,
                        AppDataBase::class.java, "weather.db")
                        .allowMainThreadQueries()
                        .build()

            }
            return INSTANCE
        }

    }
}