package com.example.alex.nilbsoftapp.ui.detailsHistory

import com.example.alex.nilbsoftapp.LifeCicle
import com.example.alex.nilbsoftapp.data.db.entity.DBEntity

interface DetailsContract {
    interface View {
        fun showCurrentData(model: DBEntity)
        fun visibilityButton(btnPrevVisibility: Int, btnNextVisibility: Int)
        fun emptyList()
    }

    interface Presenter : LifeCicle<View> {
        fun getCurrentSavedList(listWeather: List<DBEntity>)
        fun nextWeatherData()
        fun prevWeatherData()
        fun deleteItem()
    }
}