package com.nickyc975.android.model

import okhttp3.MediaType
import okhttp3.OkHttpClient

abstract class Model {
    companion object {
        @JvmStatic
        protected val client = OkHttpClient()
        @JvmStatic
        protected val JSON: MediaType = MediaType.get("application/json; charset=utf-8")
    }
}