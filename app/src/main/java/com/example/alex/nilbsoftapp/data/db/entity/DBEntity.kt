package com.example.alex.nilbsoftapp.data.db.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
data class DBEntity(val dateTime: String,
                    val coordinates: String,
                    val location: String,
                    val weatherText: String,
                    val temperature: String) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}