package com.example.alex.nilbsoftapp.utils

import android.content.Context

class StringResources(private val context: Context) {

    fun getString(resId: Int): String {
        return context.getString(resId)
    }
}