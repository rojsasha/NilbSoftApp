package com.example.alex.nilbsoftapp.ui.history


import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.example.alex.nilbsoftapp.R
import com.example.alex.nilbsoftapp.data.db.entity.DBEntity

class AdapterHistory(private  val mCallback: Callback) : RecyclerView.Adapter<AdapterHistory.ViewHolder>() {
    private var listHistory: List<DBEntity>? = null

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent!!.context)
        val lp = RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        val view = layoutInflater.inflate(R.layout.item_list, null, false)
        view.layoutParams = lp
        return ViewHolder(view)
    }

    fun setAdapterItems(list: List<DBEntity>) {
        listHistory = list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        if (listHistory != null)
            return listHistory!!.size
        return 0
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        val modelHistory = listHistory?.get(position)
        if (modelHistory != null) {
            holder!!.tvDate.text = modelHistory.dateTime
            holder.tvCoordinates.text = modelHistory.coordinates
            holder.tvLocation.text = modelHistory.location
        }
        holder!!.itemParent.setOnClickListener {
            mCallback.onClick(position)
        }
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvDate = itemView.findViewById<TextView>(R.id.tvDateTime)!!
        val tvCoordinates = itemView.findViewById<TextView>(R.id.tvCoordinates)!!
        val tvLocation = itemView.findViewById<TextView>(R.id.tvLocation)!!
        val itemParent = itemView.findViewById<LinearLayout>(R.id.itemParent)!!
    }

    interface Callback{
        fun onClick(position: Int)
    }
}