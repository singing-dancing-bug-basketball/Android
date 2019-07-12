package com.nickyc975.android

import android.content.Context
import android.os.Handler
import android.os.Message
import android.widget.Toast


interface FailHandler {
    companion object {
        enum class FailReason {
            NETWORK_ERROR,
            USER_NOT_LOGEDIN,
            USERNAME_PASSWORD_ERROR
        }

        class FailMessageHandler(val context: Context): Handler() {
            override fun handleMessage(msg: Message?) {
                super.handleMessage(msg)
                val messageId = when (msg?.peekData()?.getSerializable("reason") as FailReason) {
                    FailReason.NETWORK_ERROR -> R.string.network_error
                    FailReason.USER_NOT_LOGEDIN -> R.string.user_not_logedin
                    FailReason.USERNAME_PASSWORD_ERROR -> R.string.invalid_username_password
                }
                Toast.makeText(context, messageId, Toast.LENGTH_SHORT).show()
            }
        }
    }

    val failMessageHandler: FailMessageHandler

    fun onFail(reason: FailReason) {
        failMessageHandler.sendMessage(Message().also { message ->
            message.data.putSerializable("reason", reason)
        })
    }
}