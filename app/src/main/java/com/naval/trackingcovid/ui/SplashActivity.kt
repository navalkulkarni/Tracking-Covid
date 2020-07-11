package com.naval.trackingcovid.ui

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.naval.trackingcovid.R
import kotlinx.android.synthetic.main.activity_splash.*
import java.util.jar.Manifest

class SplashActivity : AppCompatActivity() {

    val MY_PERMISSION_CODE = 112
    val cancelText = "Sorry, but this App requires Internet in Order to Run."+
            "\n"+"Please enable Mobile Data or Wifi"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        checkForInternet()
    }


    private fun checkForInternet() {

        val cm = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        val isConnected: Boolean = activeNetwork?.isConnectedOrConnecting == true
        if(!isConnected)
        {
            setupDialog()
        }
        else{
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setupDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Device is Offline")
        builder.setMessage("Please Connect to Internet")
        builder.setPositiveButton("Go to Settings", DialogInterface.OnClickListener { dialogInterface, i ->
            val intent = Intent(Settings.ACTION_DATA_USAGE_SETTINGS)
            if (intent.resolveActivity(packageManager) != null) {
                startActivityForResult(intent,MY_PERMISSION_CODE)
            }
        })
        builder.setNegativeButton("Cancel", DialogInterface.OnClickListener { dialogInterface, i ->
            setupCancelDialog()
        } )
        val alertDialog = builder.create()
        alertDialog.show()
    }

    private fun setupCancelDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Device is Offline")
        builder.setMessage(R.string.cancel_text)
        builder.setPositiveButton("Go to Settings", DialogInterface.OnClickListener { dialogInterface, i ->
            val intent = Intent(Settings.ACTION_DATA_USAGE_SETTINGS)
            if (intent.resolveActivity(packageManager) != null) {
                startActivityForResult(intent,MY_PERMISSION_CODE)
            }
        })
        builder.setNegativeButton("Cancel", DialogInterface.OnClickListener { dialogInterface, i ->
            setupCancelDialog()
        } )
        val alertDialog = builder.create()
        alertDialog.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        checkForInternet()
    }

}