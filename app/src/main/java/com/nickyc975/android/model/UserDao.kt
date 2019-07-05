package com.nickyc975.android.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao {
    @Query("SELECT * FROM user LIMIT 1")
    fun current(): User

    @Insert
    fun insert(user: User)

    @Delete
    fun delete(user: User)
}