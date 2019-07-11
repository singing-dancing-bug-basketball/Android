package com.nickyc975.android.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [User::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    companion object {
        private var instance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase? {
            if (instance == null) {
                synchronized(AppDatabase::class.java) {
                    instance = Room.databaseBuilder(
                        context.applicationContext, AppDatabase::class.java, "app_db"
                    ).build()
                }
            }
            return instance
        }
    }

    abstract fun userDao(): UserDao
}