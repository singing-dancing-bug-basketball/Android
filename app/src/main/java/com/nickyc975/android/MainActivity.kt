package com.nickyc975.android

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import androidx.core.view.GravityCompat
import androidx.core.view.forEach
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.nickyc975.android.fragment.AboutFragment
import com.nickyc975.android.fragment.ExamsFragment
import com.nickyc975.android.fragment.HistoriesFragment
import com.nickyc975.android.fragment.ToolbarFragment

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    companion object {
        @JvmStatic
        val REQUEST_LOGIN = 0
    }

    lateinit var drawer: DrawerLayout
    lateinit var navigation: NavigationView

    private var logined = false
    private var loginLogoutButton: Button? = null

    private var drawerListener = object: DrawerLayout.DrawerListener {
        override fun onDrawerStateChanged(newState: Int) {

        }

        override fun onDrawerSlide(drawerView: View, slideOffset: Float) {

        }

        override fun onDrawerClosed(drawerView: View) {

        }

        override fun onDrawerOpened(drawerView: View) {
            if (loginLogoutButton === null) {
                loginLogoutButton = findViewById(R.id.login_logout)
                loginLogoutButton?.setText(
                    if (logined)
                        R.string.logout
                    else
                        R.string.login
                )
                loginLogoutButton?.setOnClickListener {
                    if (logined)
                        logout()
                    else
                        login()
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        drawer = findViewById(R.id.drawer)
        navigation = findViewById(R.id.navigation)

        drawer.addDrawerListener(drawerListener)
        navigation.setNavigationItemSelectedListener(this)
        if (savedInstanceState === null) {
            navigation.menu.findItem(R.id.exams).isChecked = true
            displayFragment(ExamsFragment())
        }
    }

    override fun onStart() {
        super.onStart()
        loginLogoutButton?.setText(
            if (logined)
                R.string.logout
            else
                R.string.login
        )
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_navigation_main, menu)
        return true
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        if (item.isChecked) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            when (item.itemId) {
                R.id.exams -> displayFragment(ExamsFragment())
                R.id.histories -> displayFragment(HistoriesFragment())
                R.id.about -> displayFragment(AboutFragment())
                else -> return false
            }
            navigation.menu.forEach { it.isChecked = false }
            item.isChecked = true
        }
        return true
    }

    override fun onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_LOGIN) {
            logined = data?.getBooleanExtra("logined", false) ?: false
        }
    }

    private fun displayFragment(fragment: ToolbarFragment) {
        supportFragmentManager.beginTransaction().replace(R.id.fragment_holder, fragment).commitAllowingStateLoss()
        drawer.closeDrawer(GravityCompat.START)
    }

    private fun login() {
        val loginIntent = Intent(this, LoginActivity::class.java)
        startActivityForResult(loginIntent, REQUEST_LOGIN)
    }

    private fun logout() {
        logined = false
        loginLogoutButton?.setText(R.string.login)
    }
}
