package com.nickyc975.android.model

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.room.*
import okhttp3.Request
import okhttp3.RequestBody
import org.json.JSONObject
import com.nickyc975.android.R
import java.lang.Exception

@Entity
class User(): Model() {
    companion object {
        @JvmStatic
        private val LOG_TAG = "User"

        @JvmStatic
        fun login(context: Context, id: String, password: String): User? {
            val json = "{\"id\":\"$id\", \"password\":\"$password\"}"
            val body = RequestBody.create(JSON, json)
            val request = Request.Builder()
                .url(
                    context.getString(R.string.base_url, context.getString(R.string.login_url))
                ).post(body)
                .build()

            try {
                val response = client.newCall(request).execute()
                val result = JSONObject(response.body()?.string())
                if (result.getInt("status") == 200) {
                    val cookie = result.getString("cookie")
                    if(cookie != null) {
                        val user = User(id, cookie)
                        AppDatabase.getDatabase(context)?.userDao()?.insert(user)
                        return user
                    }
                }
            } catch (e: Exception) {
                Log.d(LOG_TAG, e.message)
                Toast.makeText(context, R.string.network_error, Toast.LENGTH_SHORT).show()
            }
            return null
        }

        @JvmStatic
        fun logout(context: Context) {
            val userDao = AppDatabase.getDatabase(context)?.userDao()
            userDao?.delete(userDao.current())
        }
    }

    @PrimaryKey
    lateinit var id: String

    @ColumnInfo
    lateinit var cookie: String

    constructor(id: String, cookie: String): this() {
        this.id = id
        this.cookie = cookie
    }
}