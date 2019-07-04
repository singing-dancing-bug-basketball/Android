package com.nickyc975.android.fragment

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.nickyc975.android.MainActivity
import com.nickyc975.android.R

open class ToolbarFragment: Fragment() {
    open lateinit var toolbar: Toolbar

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbar = view.rootView.findViewById(R.id.toolbar)

        toolbar.setNavigationIcon(R.drawable.ic_menu)
        toolbar.setNavigationOnClickListener {
            (activity as MainActivity).drawer.openDrawer(GravityCompat.START)
        }
    }
}