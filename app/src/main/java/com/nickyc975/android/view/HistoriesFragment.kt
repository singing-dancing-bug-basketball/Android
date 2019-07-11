package com.nickyc975.android.view

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ListView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.nickyc975.android.HistoryActivity
import com.nickyc975.android.R
import com.nickyc975.android.adapter.HistoryAdapter
import com.nickyc975.android.model.History
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class HistoriesFragment : ToolbarFragment() {
    private var needRefresh = false
    private lateinit var handler: Handler
    private lateinit var historyList: ListView
    private lateinit var historyAdapter: HistoryAdapter
    private lateinit var historiesRefresh: SwipeRefreshLayout

    private var listener = AdapterView.OnItemClickListener { parent, view, position, id ->
        val intent = Intent(this@HistoriesFragment.context, HistoryActivity::class.java)
        intent.putExtra("history", historyAdapter.getItem(position))
        startActivity(intent)
    }

    override fun requireRefresh() {
        synchronized(needRefresh) {
            needRefresh = true
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        handler = @SuppressLint("HandlerLeak")
        object : Handler() {
            override fun handleMessage(msg: Message) {
                historyAdapter.notifyDataSetChanged()
                historiesRefresh.isRefreshing = false
            }
        }
        return inflater.inflate(R.layout.layout_histories, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbar.setTitle(R.string.history)
        historyList = view.findViewById(R.id.history_list)
        historiesRefresh = view.findViewById(R.id.histories_refresh)
        historyAdapter = HistoryAdapter(activity as Activity, listOf())
        historyList.adapter = historyAdapter
        historiesRefresh.setOnRefreshListener { }
        historyList.onItemClickListener = listener
        requireRefresh()
    }

    override fun onStart() {
        super.onStart()
        synchronized(needRefresh) {
            if (needRefresh) {
                needRefresh = false
                doRefresh()
            }
        }
    }

    private fun doRefresh() {
        historiesRefresh.isRefreshing = true
        GlobalScope.launch {
            historyAdapter.exams = History.list(this@HistoriesFragment.context!!)
            handler.sendEmptyMessage(0)
        }
    }
}