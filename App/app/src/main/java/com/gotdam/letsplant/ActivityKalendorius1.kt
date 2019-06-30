package com.gotdam.letsplant

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.prolificinteractive.materialcalendarview.*
import com.prolificinteractive.materialcalendarview.spans.DotSpan
import org.json.JSONException
import org.json.JSONObject
import org.threeten.bp.LocalDate
import java.util.*
import kotlin.collections.ArrayList

class ActivityKalendorius1 : AppCompatActivity() {
    private var mCalendarView: MaterialCalendarView? = null
    private var bdarbai: Button? = null

    internal var dates: MutableSet<CalendarDay> = HashSet()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar)
        mCalendarView = findViewById<View>(R.id.calendarView) as MaterialCalendarView
        val mypreference=SharedPrefManager(this)
        var Sarasas = ArrayList<Darbaical>()
        val stringRequest = object : StringRequest(
            Request.Method.POST, EndPoints.URL_FECTH_CALENDAR_JOBS ,
            Response.Listener<String> { s ->
                try {
                    val obj = JSONObject(s)
                    Toast.makeText(applicationContext, obj.getString("message"), Toast.LENGTH_SHORT).show()
                    if(obj.getBoolean("error")==false){

                        val array = obj.getJSONArray("Caljobs")
                        for (i in 0 .. array.length() - 1) {
                            val objectJob = array.getJSONObject(i)
                            val Jobid=objectJob.getInt("Jobid")
                            val Jobname=objectJob.getString("Jobname")
                            var Jobfrom=objectJob.getString("Jobfrom")
                            var Jobto=objectJob.getString("Jobto")
                            val darbz=Darbaical(Jobid,Jobname,Jobfrom,Jobto)
                            Sarasas.add(darbz)

                        }}



                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            object : Response.ErrorListener {
                override fun onErrorResponse(volleyError: VolleyError) {

                    Toast.makeText(applicationContext, volleyError.message, Toast.LENGTH_LONG).show()
                }
            }) {
            @Throws(AuthFailureError::class)

            override fun getParams():Map<String, String> {


                val Userid=mypreference.getID().toString()
                val Decision=mypreference.getDECISION().toString()
                    val param = HashMap<String, String>()
                    param.put("Userid", Userid)
                    param.put("Decision", Decision)

                    return param
                }}

        VolleySingleton.instance?.addToRequestQueue(stringRequest)
        //visi darbai sedi Sarasas kintamajam

        this.dates = HashSet(dates)

        for (i in 1 .. Sarasas.size)
            dates.add(CalendarDay.from(2018, 12, i))


        //var gal = Sarasas[5]


        //---------------- Chekina ar yra sarase datu, jei yra deda taska
        mCalendarView!!.addDecorator(object : DayViewDecorator {
            override fun shouldDecorate(day: CalendarDay): Boolean {
                return dates.contains(day)
            }

            override fun decorate(view: DayViewFacade) {
                view.addSpan(DotSpan(10f, Color.RED))
            }
        })
        //------------------Cia pridedineja dienas kuriose bus DotSpan


        mCalendarView!!.setOnDateChangedListener { materialCalendarView, calendarDay, b ->
            val date = "" + calendarDay.date
            val listView = findViewById<View>(R.id.datelistview) as ListView

            //-------------   ListView pridedineja
            val arrayList = ArrayList<String>()
            //arrayList.removeAll(arrayList);
            arrayList.add("Jobs for $date")
            arrayList.add(" ")

            val arrayAdapter =
                ArrayAdapter(this@ActivityKalendorius1, android.R.layout.simple_selectable_list_item, arrayList)
            listView.adapter = arrayAdapter
        }


        bdarbai = findViewById<View>(R.id.darbai) as Button
        bdarbai!!.setOnClickListener {
            val intent = Intent(this@ActivityKalendorius1, ActivityDarbai::class.java)
            startActivity(intent)
        }

    }

    companion object {
        private val TAG = "CalendarActivity"
    }
}

