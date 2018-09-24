package com.example.alex.nilbsoftapp.ui.main

import android.support.v4.app.Fragment

class TabMain(private val mFragment: Fragment,
              private val mTitle: CharSequence) {


    fun getFragment(): Fragment {
        return mFragment
    }

    fun getTitle(): CharSequence {
        return mTitle
    }
}