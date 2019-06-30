package com.gotdam.letsplant

import android.annotation.SuppressLint
import android.app.Activity
import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.support.design.widget.TabLayout
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.PopupMenu
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.gotdam.letsplant.R.layout.activity_popup
import com.gotdam.letsplant.R.layout.activity_sarasas
import kotlinx.android.synthetic.main.activity_darbai.*
import kotlinx.android.synthetic.main.activity_popup.*
import kotlinx.android.synthetic.main.activity_sarasas.*
import kotlinx.android.synthetic.main.activity_sarasas.view.*
import org.json.JSONException
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import android.content.SharedPreferences
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class ActivityDarbai : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_darbai)


        val adapter = ViewPageradapter(supportFragmentManager)

        adapter.addFragment(Sausis(), "January")
        adapter.addFragment(Vasaris(), "February")
        adapter.addFragment(Kovas(), "March")
        adapter.addFragment(Balandis(), "April")
        adapter.addFragment(Geguze(), "May")
        adapter.addFragment(Birzelis(), "June")
        adapter.addFragment(Liepa(), "July")
        adapter.addFragment(Rugpjutis(), "August")
        adapter.addFragment(Rugsejis(), "September")
        adapter.addFragment(Spalis(), "October")
        adapter.addFragment(Lapkritis(), "November")
        adapter.addFragment(Gruodis(), "December")

        viewpager.adapter = adapter
        tabs.setupWithViewPager(viewpager)

        val Today = Calendar.getInstance()
        //menesis.setText(SimpleDateFormat("Y-M-d").format(Today.time))

        calendar.setOnClickListener {
            val intent: Intent = Intent(applicationContext, ActivityKalendorius1::class.java)
            startActivity(intent)
        }
        atgal.setOnClickListener {
            val intent: Intent = Intent(applicationContext, ActivityPasirinkimas::class.java)
            startActivity(intent)
        }

    }
    class ViewPageradapter (manager: FragmentManager): FragmentPagerAdapter(manager){

        private val fragmentlist: MutableList<Fragment> = ArrayList()
        private val titlelist: MutableList<String> = ArrayList()

        override fun getItem(position: Int): Fragment {
            return fragmentlist[position]
        }

        override fun getCount(): Int {
            return fragmentlist.size
        }
        fun addFragment(fragment: Fragment, title: String){
            fragmentlist.add(fragment)
            titlelist.add(title)
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return titlelist[position]
        }
    }

}
