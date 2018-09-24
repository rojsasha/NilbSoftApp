package com.example.alex.nilbsoftapp.ui.weather

import android.content.Context.LOCATION_SERVICE
import android.location.LocationManager
import android.os.Bundle
import android.support.v4.app.Fragment
import android.widget.TextView
import com.example.alex.nilbsoftapp.R
import com.example.alex.nilbsoftapp.utils.PermissionUtils
import android.content.pm.PackageManager
import android.location.Geocoder
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import com.example.alex.nilbsoftapp.StartApplication
import com.example.alex.nilbsoftapp.utils.AppConstants
import com.example.alex.nilbsoftapp.utils.StringResources
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_weather.*
import java.util.*


class WeatherFragment : Fragment(), WeatherContract.View {

    private var mPresenter: WeatherContract.Presenter? = null
    private var mTvCoordinates: TextView? = null
    private var mTvLocation: TextView? = null
    private var mImageWeather: ImageView? = null
    private var mTvWeatherText: TextView? = null
    private var mTvTemperature: TextView? = null
    private var mProgressBar: ProgressBar? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_weather, container, false)
    }

    private fun initPresenter() {
        mPresenter = WeatherPresenter(context!!.getSystemService(LOCATION_SERVICE) as LocationManager,
                StartApplication.get(context!!).getService(),
                Geocoder(context, Locale.getDefault()),
                StringResources(context!!),
                StartApplication.get(context!!).getDB())
        mPresenter!!.bind(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewElement(view)
        setHasOptionsMenu(true)

        initPresenter()

        checkLocation()
    }

    private fun initViewElement(view: View) {
        mTvLocation = view.findViewById(R.id.tvLocation)
        mTvCoordinates = view.findViewById(R.id.tvCoordinates)
        mImageWeather = view.findViewById(R.id.imgWeather)
        mTvWeatherText = view.findViewById(R.id.tvWeatherText)
        mTvTemperature = view.findViewById(R.id.tvTemperature)
        mProgressBar = view.findViewById(R.id.progressBar)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater!!.inflate(R.menu.menu_refresh,menu)
    }



    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.refresh -> refreshWeather()
        }
        return super.onOptionsItemSelected(item)
    }

    fun permissionResult() {
        checkLocation()
    }

    fun refreshWeather() {
        checkLocation()
    }

    private fun checkLocation() {
        if (PermissionUtils.checkLocationPermission(activity!!)) {
            mPresenter!!.getLocation()
            Log.d("activity", "location good")
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        Log.d("permission", "start")
        if (requestCode == AppConstants.REQUEST_CODE_LOCATION_PERMISSION) {
            for (result in grantResults) {
                if (result == PackageManager.PERMISSION_GRANTED) {
                    mPresenter!!.getLocation()
                } else {
                    checkLocation()
                }
            }
        }
    }

    override fun tvSetCoordinates(coordinates: String) {
        mTvCoordinates!!.text = coordinates
    }

    override fun tvSetLocation(location: String) {
        mTvLocation!!.text = location
    }

    override fun setWeatherData(img: String, weatherText: String, temperature: String) {
        tvWeatherText.text = weatherText
        tvTemperature.text = temperature

        Picasso.get()
                .load(img)
                .into(mImageWeather)
    }

    override fun showProgressBar() {
        mProgressBar!!.visibility = View.VISIBLE
    }

    override fun hideProgressBar() {
        mProgressBar!!.visibility = View.GONE
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter!!.unbind()
    }
}