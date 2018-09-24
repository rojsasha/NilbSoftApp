package com.example.alex.nilbsoftapp.ui.history

import android.arch.lifecycle.Observer
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import com.example.alex.nilbsoftapp.R
import com.example.alex.nilbsoftapp.StartApplication
import com.example.alex.nilbsoftapp.data.db.entity.DBEntity
import com.example.alex.nilbsoftapp.ui.detailsHistory.DetailsActivity
import com.example.alex.nilbsoftapp.ui.detailsHistory.WeatherHistoryDeleteDialog


class HistoryResultFragment : Fragment(), AdapterHistory.Callback {

    private var mRecyclerView: RecyclerView? = null
    private var mAdapter: AdapterHistory? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewElement(view)

        setHasOptionsMenu(true)

        observe()
    }

    private fun initViewElement(view: View) {
        mRecyclerView = view.findViewById(R.id.recyclerView)
        val layoutManager = LinearLayoutManager(context)

        mRecyclerView!!.layoutManager = layoutManager
        mAdapter = AdapterHistory(this)
        mRecyclerView!!.adapter = mAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater!!.inflate(R.menu.menu_delete_all, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val db = StartApplication.get(context!!).getDB()
        db!!.dbDAO().deleteAllHistory()
        return super.onOptionsItemSelected(item)

    }

    private fun observe() {
        val db = StartApplication.get(context!!).getDB()
        val historyObserver: Observer<List<DBEntity>> = Observer {
            mAdapter!!.setAdapterItems(it!!)
        }

        val weatherLiveData = db!!.dbDAO().getSavedHistory()
        weatherLiveData.observe(this, historyObserver)
    }


    override fun onClick(position: Int) {
        startActivity(Intent(context, DetailsActivity::class.java)
                .putExtra("position", position))
    }
}