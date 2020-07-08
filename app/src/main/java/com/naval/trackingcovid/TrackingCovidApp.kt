package com.naval.trackingcovid

import android.app.Application
import com.androidnetworking.AndroidNetworking
import com.naval.trackingcovid.db.DatabaseService


class TrackingCovidApp : Application() {

    override fun onCreate() {
        super.onCreate()
        AndroidNetworking.initialize(applicationContext)
    }

}