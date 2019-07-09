package com.nickyc975.android.view

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import com.nickyc975.android.R
import com.nickyc975.android.adapter.HistoryAdapter

class HistoriesFragment: ToolbarFragment() {
    override fun requireRefresh() {

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.layout_histories, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbar.setTitle(R.string.history)
        // (activity?.findViewById(R.id.history_list) as ListView).adapter = HistoryAdapter(activity as Activity, Data.histories)
    }
}