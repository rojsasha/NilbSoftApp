package com.example.alex.nilbsoftapp.data.db

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.example.alex.nilbsoftapp.data.db.entity.DBEntity

@Dao
interface DatabaseDAO {

    @Insert
    fun saveToHistory(model: DBEntity)

    @Query("SELECT * FROM DBEntity")
    fun getSavedHistory(): LiveData<List<DBEntity>>

    @Query("DELETE FROM DBEntity")
    fun deleteAllHistory()

    @Delete
    fun deleteCurrentItem(model: DBEntity)
}