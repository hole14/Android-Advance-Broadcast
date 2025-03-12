package com.example.mybroadcastreceiver

import android.content.BroadcastReceiver
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.provider.Telephony
import android.util.Log

class SMSReceiver : BroadcastReceiver() {
    companion object{

    }
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Telephony.Sms.Intents.SMS_RECEIVED_ACTION){
            val messages = Telephony.Sms.Intents.getMessagesFromIntent(intent)
            for (message in messages){
                val senderNum = message.originatingAddress
                val body = message.messageBody
                Log.d(TAG, "senderNum: $senderNum; message: $body")
                val showSmsIntent = Intent(context, SMSReceiverActivity::class.java)
                showSmsIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                showSmsIntent.putExtra(SMSReceiverActivity.EXTRA_SMS_NO, senderNum)
                showSmsIntent.putExtra(SMSReceiverActivity.EXTRA_SMS_MESSAGE, body)
                context.startActivity(showSmsIntent)
            }

        }
    }
}