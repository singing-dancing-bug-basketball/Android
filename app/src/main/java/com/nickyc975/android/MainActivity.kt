package com.nickyc975.android

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private var logined = false
    private lateinit var toolbar: Toolbar
    private lateinit var drawer: DrawerLayout
    private lateinit var navigation: NavigationView
    private lateinit var menuButton: ImageButton

    private var drawerListener = object: DrawerLayout.DrawerListener {
        override fun onDrawerStateChanged(newState: Int) {

        }

        override fun onDrawerSlide(drawerView: View, slideOffset: Float) {

        }

        override fun onDrawerClosed(drawerView: View) {

        }

        override fun onDrawerOpened(drawerView: View) {
            val loginLogout: Button = findViewById(R.id.login_logout)
            loginLogout.setText(if (logined) R.string.logout else R.string.login)
            loginLogout.setOnClickListener {
                if (logined) {
                    logout()
                    loginLogout.setText(R.string.login)
                } else {
                    login()
                    loginLogout.setText(R.string.logout)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        toolbar = findViewById(R.id.toolbar)
        drawer = findViewById(R.id.drawer)
        navigation = findViewById(R.id.navigation)
        menuButton = findViewById(R.id.menu_button)
        menuButton.setOnClickListener { drawer.openDrawer(GravityCompat.START) }
        drawer.addDrawerListener(drawerListener)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_navigation_main, menu)
        return true
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.history -> return true
            R.id.about -> return true
            else -> return false
        }
    }

    override fun onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    private fun login() {
        val loginIntent = Intent(this, Login::class.java)
        startActivity(loginIntent)
        logined = true
    }

    private fun logout() {
        logined = false
    }
}
