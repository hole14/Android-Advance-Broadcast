package com.example.mybroadcastreceiver

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.mybroadcastreceiver.databinding.ActivitySmsreceiverBinding

class SMSReceiverActivity : AppCompatActivity() {
    companion object{
        const val EXTRA_SMS_NO = "extra_sms_no"
        const val EXTRA_SMS_MESSAGE = "extra_sms_message"
    }
    private var binding: ActivitySmsreceiverBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySmsreceiverBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        title = getString(R.string.incoming_message)
        binding?.tvFrom?.text = getString(R.string.from, intent.getStringExtra(EXTRA_SMS_NO))
        binding?.tvMessage?.text = intent.getStringExtra(EXTRA_SMS_MESSAGE)
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}