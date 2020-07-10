package com.naval.trackingcovid.utils

import android.app.ProgressDialog
import android.os.AsyncTask
import com.naval.trackingcovid.ui.TakeReadingActivity
import javax.mail.Message
import javax.mail.MessagingException
import javax.mail.Transport

class SendMail(takeReadingActivity: TakeReadingActivity) : AsyncTask<Message, String, String>() {

    lateinit var progressDialog : ProgressDialog
    val activityContext = takeReadingActivity

    override fun onPreExecute() {
        super.onPreExecute()
        progressDialog = ProgressDialog.show(activityContext,"Please Wait..","Sending Mail..",true,false)

    }

    override fun onPostExecute(result: String?) {
        super.onPostExecute(result)
        progressDialog.dismiss()
    }

    override fun doInBackground(vararg p0: javax.mail.Message?): String {
        try {
            Transport.send(p0[0] as javax.mail.Message)
            return "Success"
        }catch( e : MessagingException){
            e.printStackTrace()
            return  "Error"
        }
    }
}
