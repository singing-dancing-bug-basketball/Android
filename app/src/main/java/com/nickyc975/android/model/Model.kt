package com.nickyc975.android.model

import okhttp3.MediaType
import okhttp3.OkHttpClient

abstract class Model {
    companion object {
        @JvmStatic
        protected val client = OkHttpClient()
        @JvmStatic
        protected val JSON: MediaType = MediaType.get("application/json; charset=utf-8")
        @JvmStatic
        protected val BASE_URL = "http://192.168.43.136:80/student/"
        @JvmStatic
        protected val LOGIN_URL = "login/"
        @JvmStatic
        protected val EXAM_URL = "test/"
        @JvmStatic
        protected val EXAM_LIST_URL = "test/list"
        @JvmStatic
        protected val HISTORY_URL = "record/"
        @JvmStatic
        protected val HISTORY_LIST_URL = "record/list"

        @JvmStatic
        protected lateinit var db: AppDatabase

        fun setDatabase(db: AppDatabase) {
            Model.db = db
        }
    }
}