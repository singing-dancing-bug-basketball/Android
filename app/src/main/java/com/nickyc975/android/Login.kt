package com.nickyc975.android

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class Login : AppCompatActivity() {
    private lateinit var loginButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        loginButton = findViewById(R.id.login)
        loginButton.setOnClickListener {
            val data = Intent()
            data.putExtra("logined", true)
            setResult(MainActivity.REQUEST_LOGIN, data)
            finish()
        }
    }
}
