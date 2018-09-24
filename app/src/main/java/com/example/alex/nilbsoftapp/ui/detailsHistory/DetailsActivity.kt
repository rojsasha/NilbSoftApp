package com.example.alex.nilbsoftapp.ui.detailsHistory

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.example.alex.nilbsoftapp.R
import com.example.alex.nilbsoftapp.StartApplication
import com.example.alex.nilbsoftapp.data.db.entity.DBEntity
import kotlinx.android.synthetic.main.activity_details.*

class DetailsActivity : AppCompatActivity(), DetailsContract.View, View.OnClickListener,WeatherHistoryDeleteDialog.OnClickDialog {

    private var mPresenter: DetailsContract.Presenter? = null
    private var mTvDate: TextView? = null
    private var mTvCoordinates: TextView? = null
    private var mTvLocation: TextView? = null
    private var mTvWeatherText: TextView? = null
    private var mTvTemperature: TextView? = null
    private var mBtnPrev: LinearLayout? = null
    private var mBtnNext: LinearLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        initViewElement()

        mPresenter = DetailsPresenter(intent, StartApplication.get(this).getDB())
        mPresenter!!.bind(this)

        observe()
    }

    private fun observe() {
        val db = StartApplication.get(this).getDB()
        val historyObserver: Observer<List<DBEntity>> = Observer {
            mPresenter!!.getCurrentSavedList(it!!)
        }

        val weatherLiveData = db!!.dbDAO().getSavedHistory()
        weatherLiveData.observe(this, historyObserver)
    }

    private fun initViewElement() {
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        mTvDate = findViewById(R.id.tvDetailsDate)
        mTvCoordinates = findViewById(R.id.tvDetailsCoordinates)
        mTvLocation = findViewById(R.id.tvDetailsLocation)
        mTvTemperature = findViewById(R.id.tvDetailsTemperature)
        mTvWeatherText = findViewById(R.id.tvDetailsWeatherText)
        mBtnPrev = findViewById(R.id.btnPrev)
        mBtnNext = findViewById(R.id.btnNext)

        btnPrev.setOnClickListener(this)
        btnNext.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.btnPrev -> mPresenter!!.prevWeatherData()
            R.id.btnNext -> mPresenter!!.nextWeatherData()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_details, menu)
        return true
    }

    override fun emptyList() {
        onBackPressed()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.deleteItem -> {
                val dialogFragment = WeatherHistoryDeleteDialog()
                val bundle  = Bundle()
                bundle.putString("title", getString(R.string.dialog_delete))
                dialogFragment.arguments = bundle
                dialogFragment.show(supportFragmentManager, "showDialogFragment")
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun showCurrentData(model: DBEntity) {
        mTvDate!!.text = model.dateTime
        mTvLocation!!.text = model.location
        mTvWeatherText!!.text = model.weatherText
        mTvTemperature!!.text = model.temperature
    }

    override fun visibilityButton(btnPrevVisibility: Int, btnNextVisibility: Int) {
        mBtnNext!!.visibility = btnNextVisibility
        mBtnPrev!!.visibility = btnPrevVisibility
    }

    override fun clickPositiveButton() {
        mPresenter!!.deleteItem()
    }


    override fun onDestroy() {
        super.onDestroy()
        mPresenter!!.unbind()
    }

}