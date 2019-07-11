package com.nickyc975.android.model

import android.content.Context
import android.util.Log
import androidx.room.*
import com.nickyc975.android.LoginActivity
import okhttp3.Request
import okhttp3.RequestBody
import org.json.JSONObject
import com.nickyc975.android.R
import com.nickyc975.android.FailReason
import java.io.Serializable
import java.lang.Exception

@Entity
class User(): Model(), Serializable {
    companion object {
        @JvmStatic
        private val LOG_TAG = "UserModel"

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
                    val cookie = response.header("Set-Cookie")
                    if(cookie != null) {
                        val user = User(id, cookie)
                        AppDatabase.getDatabase(context)?.userDao()?.insert(user)
                        return user
                    }
                }
                (context as LoginActivity).failedReason = FailReason.USERNAME_PASSWORD_ERROR
            } catch (e: Exception) {
                Log.e(LOG_TAG, e.message)
                (context as LoginActivity).failedReason = FailReason.NETWORK_ERROR
            }

            return null
        }

        @JvmStatic
        fun isLogedin(context: Context): Boolean {
            return AppDatabase.getDatabase(context)?.userDao()?.count() ?: 0 > 0
        }

        @JvmStatic
        fun logout(context: Context) {
            if (isLogedin(context)) {
                val userDao = AppDatabase.getDatabase(context)?.userDao()
                userDao?.delete(userDao.current())
            }
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

@Dao
interface UserDao {
    @Query("SELECT COUNT(*) FROM user")
    fun count(): Int

    @Query("SELECT * FROM user LIMIT 1")
    fun current(): User

    @Insert
    fun insert(user: User)

    @Delete
    fun delete(user: User)
}