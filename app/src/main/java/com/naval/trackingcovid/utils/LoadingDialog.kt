package com.naval.trackingcovid.utils

import android.app.Activity
import android.app.AlertDialog
import android.os.Handler
import android.os.HandlerThread
import com.naval.trackingcovid.R

class LoadingDialog {

    companion object{

        lateinit var alertDialog: AlertDialog
        fun show(activity: Activity,dialogText:String){
            val builder = AlertDialog.Builder(activity)
            val inflater = activity.layoutInflater
            builder.setView(inflater.inflate(R.layout.custom_alert_dialog,null))
            builder.setMessage(dialogText)
            builder.setCancelable(false)
            alertDialog = builder.create()
            alertDialog.show()

        }

        fun dismiss(){
            var handler: Handler = Handler()
            handler.postDelayed({
                alertDialog.dismiss()
            },2000)
        }

    }

}