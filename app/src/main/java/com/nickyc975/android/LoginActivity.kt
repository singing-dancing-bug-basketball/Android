package com.nickyc975.android

import android.annotation.SuppressLint
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.nickyc975.android.model.User

class LoginActivity : AppCompatActivity(), FailHandler {
    override val failMessageHandler = FailHandler.Companion.FailMessageHandler(this)

    private lateinit var username: TextView
    private lateinit var password: TextView
    private lateinit var loginButton: Button

    private val listener = View.OnClickListener {
        val uname = username.text.toString()
        val passwd = password.text.toString()
        if (uname.isBlank() || passwd.isEmpty()) {
            Toast.makeText(this, R.string.invalid_username_password, Toast.LENGTH_SHORT).show()
        } else {
            username.isEnabled = false
            password.isEnabled = false
            loginButton.isEnabled = false
            LoginTask().execute(uname, passwd)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        username = findViewById(R.id.username)
        password = findViewById(R.id.password)
        loginButton = findViewById(R.id.login)
        loginButton.setOnClickListener(listener)
    }

    private fun onLoginDone(user: User?) {
        username.isEnabled = true
        password.isEnabled = true
        loginButton.isEnabled = true
        if (user !== null) {
            val intent = Intent()
            intent.putExtra("user", user)
            setResult(MainActivity.REQUEST_LOGIN, intent)
            finish()
        }
    }

    @SuppressLint("StaticFieldLeak")
    inner class LoginTask : AsyncTask<String, Void, User?>() {
        override fun doInBackground(vararg params: String?): User? {
            return User.login(this@LoginActivity, params[0].orEmpty(), params[1].orEmpty())
        }

        override fun onPostExecute(result: User?) {
            super.onPostExecute(result)
            onLoginDone(result)
        }
    }
}
