package com.example.alex.nilbsoftapp.ui.main

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter

class TabStateAdapter(fm: FragmentManager?, private val mTabs: ArrayList<TabMain>) : FragmentStatePagerAdapter(fm) {

    fun getFirstFragment(): Fragment {
        return mTabs[0].getFragment()
    }


    override fun getItem(p0: Int): Fragment {
        return mTabs[p0].getFragment()
    }

    override fun getCount(): Int {
        return mTabs.size
    }

    override fun getPageTitle(position: Int): CharSequence {
        return mTabs[position].getTitle()
    }
}