package com.example.alex.nilbsoftapp.ui.detailsHistory

import android.content.Intent
import android.view.View
import com.example.alex.nilbsoftapp.data.db.AppDataBase
import com.example.alex.nilbsoftapp.data.db.entity.DBEntity

class DetailsPresenter(intent: Intent, private val db: AppDataBase?) : DetailsContract.Presenter {

    private var position: Int = 0
    private var listWeather: List<DBEntity>? = null

    private var mView: DetailsContract.View? = null

    init {
        position = intent.getIntExtra("position", 0)
    }

    override fun getCurrentSavedList(listWeather: List<DBEntity>) {
        this.listWeather = listWeather
        if (listWeather.isEmpty()) {
            mView!!.emptyList()
        } else {
            mView!!.showCurrentData(listWeather[position])
            disableVisibilityButtons()
        }

    }

    override fun prevWeatherData() {
        position -= 1
        disableVisibilityButtons()
        getSavedWeather()
    }

    override fun nextWeatherData() {
        position += 1
        disableVisibilityButtons()
        getSavedWeather()
    }


    private fun disableVisibilityButtons() {
        when {
            listWeather!!.size == 1 -> mView!!.visibilityButton(View.INVISIBLE, View.INVISIBLE)
            listWeather!!.size - 1 == position -> mView!!.visibilityButton(View.VISIBLE, View.INVISIBLE)
            position == 0 -> mView!!.visibilityButton(View.INVISIBLE, View.VISIBLE)
            else -> mView!!.visibilityButton(View.VISIBLE, View.VISIBLE)
        }
    }

    private fun getSavedWeather() {
        mView!!.showCurrentData(listWeather!![position])
    }

    override fun deleteItem() {
        db!!.dbDAO().deleteCurrentItem(listWeather!![position])
        if (position != 0)
            position -= 1


        disableVisibilityButtons()
        getSavedWeather()
    }

    override fun unbind() {
        mView = null
    }

    override fun bind(view: DetailsContract.View) {
        mView = view
    }
}