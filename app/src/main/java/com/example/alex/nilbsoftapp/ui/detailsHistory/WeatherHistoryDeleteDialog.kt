package com.example.alex.nilbsoftapp.ui.detailsHistory

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import com.example.alex.nilbsoftapp.R

class WeatherHistoryDeleteDialog: DialogFragment() {
    private var mCallback : OnClickDialog? = null
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
            val alertDialog: AlertDialog.Builder = AlertDialog.Builder(activity!!)
        alertDialog.setMessage(arguments!!.getString("title"))
                .setPositiveButton(getString(R.string.dialog_positive)) { _, _ -> mCallback!!.clickPositiveButton() }
                .setNegativeButton(getString(R.string.dialog_negative)) { _: DialogInterface, _: Int -> dismiss()}

        return alertDialog.create()
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        mCallback = context as OnClickDialog
    }


    interface OnClickDialog{
        fun clickPositiveButton()
    }
}