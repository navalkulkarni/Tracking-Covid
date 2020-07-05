package com.naval.trackingcovid.ui

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.transition.Visibility
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.naval.trackingcovid.R
import kotlinx.android.synthetic.main.add_user_activity.*

class AddUserActivity : AppCompatActivity(){



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_user_activity)
        Log.d("AddUser","In Add User Acitivity")
        setupClickListeners()

    }

    private fun setupClickListeners() {
        createUserButton.setOnClickListener {
            startTakingReadingButton.visibility = View.VISIBLE
        }

        startTakingReadingButton.setOnClickListener {
            val intent = Intent(this,TakeReadingActivity::class.java)
            startActivity(intent)
        }
    }

}