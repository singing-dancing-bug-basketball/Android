package com.nickyc975.android.model

import androidx.room.*
import okhttp3.Request
import okhttp3.RequestBody
import org.json.JSONObject

@Entity
class User(): Model() {
    @PrimaryKey
    lateinit var id: String

    @ColumnInfo
    lateinit var cookie: String

    constructor(id: String, cookie: String): this() {
        this.id = id
        this.cookie = cookie
    }

    companion object {
        @JvmStatic
        fun login(id: String, password: String): User? {
            val json = "{\"id\":\"$id\", \"password\":\"$password\"}"
            val body = RequestBody.create(JSON, json)
            val request = Request.Builder().url(BASE_URL + LOGIN_URL).post(body).build()
            val response = client.newCall(request).execute()
            val result = JSONObject(response.body()?.string())
            if (result.getInt("status") == 200) {
                val cookie = result.getString("cookie")
                if(cookie != null) {
                    val user = User(id, cookie)
                    db.userDao().insert(user)
                    return user
                }
            }
            return null
        }

        @JvmStatic
        fun logout() {
            val userDao = db.userDao()
            userDao.delete(userDao.current())
        }
    }
}