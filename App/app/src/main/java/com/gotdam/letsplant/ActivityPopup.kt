package com.gotdam.letsplant

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_popup.*

class ActivityPopup : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_popup)

        ideti.setOnClickListener {
            Toast.makeText(applicationContext, "Paspaudete ideti", Toast.LENGTH_LONG).show()
        }
        ismesti.setOnClickListener {
            Toast.makeText(applicationContext, "Paspaudete ismesti", Toast.LENGTH_LONG).show()
        }

    }
}