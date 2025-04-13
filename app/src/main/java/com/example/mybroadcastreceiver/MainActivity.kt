package com.example.mybroadcastreceiver

import android.os.Bundle
import android.view.View
import android.widget.Toast
import android.Manifest
import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Handler
import android.os.Looper
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.mybroadcastreceiver.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private var binding: ActivityMainBinding? = null
    companion object{
        const val ACTION_DOWNLOAD_STATUS = "download_status"
    }
    private lateinit var downloadReceiver: BroadcastReceiver
//    @SuppressLint("UnspecifiedRegisterReceiverFlag")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        binding?.btnPermission?.setOnClickListener (this)
        binding?.btnDownload?.setOnClickListener (this)

        downloadReceiver = object : BroadcastReceiver(){
            override fun onReceive(context: Context?, intent: Intent?) {
                Toast.makeText(context, "Download Selesai", Toast.LENGTH_SHORT).show()
            }
        }
        val downloadIntentFilter = IntentFilter(ACTION_DOWNLOAD_STATUS)
//        registerReceiver(downloadReceiver, downloadIntentFilter)
        ContextCompat.registerReceiver(this, downloadReceiver, downloadIntentFilter, ContextCompat.RECEIVER_EXPORTED)
    }

    private var requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            Toast.makeText(this, "Sms receiver permission diterima", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Sms receiver permission ditolak", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.btn_permission -> requestPermissionLauncher.launch(Manifest.permission.RECEIVE_SMS)
            R.id.btn_download -> {
                //simulasi download prosess in 3 seconds
                Handler(Looper.getMainLooper()).postDelayed({
                    val notifyFinishIntent = Intent().setAction(ACTION_DOWNLOAD_STATUS)
                    sendBroadcast(notifyFinishIntent)
                }, 3000)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(downloadReceiver)
        binding = null
    }
}