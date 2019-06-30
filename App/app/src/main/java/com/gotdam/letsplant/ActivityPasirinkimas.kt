package com.gotdam.letsplant

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_choice.*

class ActivityPasirinkimas : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choice)

    //nusiust mindei kuri pasirinkima pasirinko

        darzininkas.setOnClickListener {
            Toast.makeText(this, "You've chosen gardener", Toast.LENGTH_SHORT).show()
            val mypreference = SharedPrefManager(this)
            val pasirinkti = "pirmas"
            mypreference.setDECISION(pasirinkti.toString())
            val intent: Intent = Intent(applicationContext, ActivityDarbai::class.java)
            startActivity(intent)

        }
        sodininkas.setOnClickListener {
            Toast.makeText(this, "You've chosen fruiter", Toast.LENGTH_SHORT).show()
            val mypreference = SharedPrefManager(this)
            val pasirinkti = "trecias"
            mypreference.setDECISION(pasirinkti.toString())
            val intent: Intent = Intent(applicationContext, ActivityDarbai::class.java)

            startActivity(intent)

        }
        gelininkas.setOnClickListener {
            Toast.makeText(this, "You've chosen florist", Toast.LENGTH_SHORT).show()
            val mypreference = SharedPrefManager(this)
            val pasirinkti = "antras"
            mypreference.setDECISION(pasirinkti.toString())

            val intent: Intent = Intent(applicationContext, ActivityDarbai::class.java)

            startActivity(intent)

        }
    }
}
