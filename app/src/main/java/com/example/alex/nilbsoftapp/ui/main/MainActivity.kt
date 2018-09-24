package com.example.alex.nilbsoftapp.ui.main

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.support.v7.widget.Toolbar
import android.util.Log
import com.example.alex.nilbsoftapp.R
import com.example.alex.nilbsoftapp.ui.history.HistoryResultFragment
import com.example.alex.nilbsoftapp.ui.weather.WeatherFragment

class MainActivity : AppCompatActivity() {
    private var adapter: TabStateAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        createTab()
    }

    private fun createTab() {
        val tabs: ArrayList<TabMain> = ArrayList()
        tabs.add(TabMain(WeatherFragment(), "Погода"))
        tabs.add(TabMain(HistoryResultFragment(), "История поиска"))
        val viewPager = findViewById<ViewPager>(R.id.viewPager)
        adapter = TabStateAdapter(supportFragmentManager, tabs)
        viewPager.adapter = adapter
        val tabLayout = findViewById<TabLayout>(R.id.tabLayout)
        tabLayout.setupWithViewPager(viewPager)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        Log.d("main", "permission")
        val fragment = adapter!!.getFirstFragment()
        (fragment as WeatherFragment).permissionResult()
    }


}
