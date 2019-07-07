package com.nickyc975.android

import android.annotation.SuppressLint
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.core.view.GravityCompat
import androidx.core.view.forEach
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.nickyc975.android.model.AppDatabase
import com.nickyc975.android.view.AboutFragment
import com.nickyc975.android.view.ExamsFragment
import com.nickyc975.android.view.HistoriesFragment
import com.nickyc975.android.view.ToolbarFragment
import com.nickyc975.android.model.User

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    companion object {
        @JvmStatic
        val REQUEST_LOGIN = 0
    }

    lateinit var drawer: DrawerLayout
    private lateinit var navigation: NavigationView
    private lateinit var currentFragment: ToolbarFragment

    private var user: User? = null
    private var database: AppDatabase? = null

    private var userIdTextView: TextView? = null
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
                userIdTextView = findViewById(R.id.drawer_title)
                loginLogoutButton = findViewById(R.id.login_logout)

                if (user !== null) {
                    userIdTextView?.text = user?.id
                    loginLogoutButton?.setText(R.string.logout)
                } else {
                    userIdTextView?.text = ""
                    loginLogoutButton?.setText(R.string.login)
                }

                loginLogoutButton?.setOnClickListener {
                    if (user !== null)
                        logout()
                    else
                        login()
                }
            }
        }
    }

    private fun setCurrentFragment(fragment: ToolbarFragment) {
        supportFragmentManager.beginTransaction().replace(R.id.fragment_holder, fragment).commitAllowingStateLoss()
        drawer.closeDrawer(GravityCompat.START)
        currentFragment = fragment
    }

    @SuppressLint("StaticFieldLeak")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        drawer = findViewById(R.id.drawer)
        navigation = findViewById(R.id.navigation)

        drawer.addDrawerListener(drawerListener)
        navigation.setNavigationItemSelectedListener(this)

        if (savedInstanceState === null) {
            navigation.menu.findItem(R.id.exams).isChecked = true
            setCurrentFragment(ExamsFragment())

            object: AsyncTask<Void, Void, Void?>() {
                override fun doInBackground(vararg params: Void?): Void? {
                    database = AppDatabase.getDatabase(this@MainActivity)
                    if (User.isLogedin(this@MainActivity)) {
                        user = database?.userDao()?.current()
                    } else {
                        user = User("123456789", "askkdfaskjdhnflkalskdj")
                        database?.userDao()?.insert(user!!)
                    }
                    currentFragment.requireRefresh()
                    return null
                }
            }.execute()
        }
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
                R.id.exams -> setCurrentFragment(ExamsFragment())
                R.id.histories -> setCurrentFragment(HistoriesFragment())
                R.id.about -> setCurrentFragment(AboutFragment())
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
            user = data?.getSerializableExtra("user") as User?
            if (user !== null) {
                userIdTextView?.text = user?.id
                loginLogoutButton?.setText(R.string.logout)
                currentFragment.requireRefresh()
            }
        }
    }

    private fun login() {
        val loginIntent = Intent(this, LoginActivity::class.java)
        startActivityForResult(loginIntent, REQUEST_LOGIN)
    }

    @SuppressLint("StaticFieldLeak")
    private fun logout() {
        object: AsyncTask<Void, Void, Void?>() {
            override fun doInBackground(vararg params: Void?): Void? {
                User.logout(this@MainActivity)
                return null
            }
        }

        user = null
        userIdTextView?.text = ""
        loginLogoutButton?.setText(R.string.login)
    }
}
