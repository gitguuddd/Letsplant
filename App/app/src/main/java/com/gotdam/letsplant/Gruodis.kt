package com.gotdam.letsplant


import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.GetChars
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.BaseAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_darbai.*
import kotlinx.android.synthetic.main.activity_sarasas.view.*
import kotlinx.android.synthetic.main.fragment_gruodis.*
import java.util.*
import org.json.JSONException
import org.json.JSONObject
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import java.sql.Date
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar



class Gruodis : Fragment() {
    var adapteris: Gruodis.Adapteris? = null
    var Sarasas = ArrayList<Darbai>()




    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_gruodis, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        val mypreference= SharedPrefManager(context!!)
        val Decision=mypreference.getDECISION()
        val Userid=mypreference.getID().toString()
        val now = Calendar.getInstance()
        val toformat= LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")



        val stringRequest = object : StringRequest(Request.Method.POST, EndPoints.URL_FECTH_JOBS ,
            Response.Listener<String> { s ->
                try {
                    val obj = JSONObject(s)
                    Toast.makeText(context!!, obj.getString("message"), Toast.LENGTH_SHORT).show()
                    if(obj.getBoolean("error")==false){

                        val array = obj.getJSONArray("Jobs")
                        for (i in 0 .. array.length() - 1) {
                            val objectJob = array.getJSONObject(i)
                            val Jobid=objectJob.getInt("Jobid")
                            val data=objectJob.getString("Jobdate")
                            val darbas=objectJob.getString("Jobname")
                            val darbz=Darbai(Jobid,data,darbas,R.mipmap.kas)
                            Sarasas.add(darbz)


                        }}



                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            object : Response.ErrorListener {
                override fun onErrorResponse(volleyError: VolleyError) {

                    Toast.makeText(context!!, volleyError.message, Toast.LENGTH_LONG).show()
                }
            }) {
            @Throws(AuthFailureError::class)

            override fun getParams():Map<String, String> {
                if((now.get(Calendar.MONTH)+1)==12){
                    val data=toformat.format(formatter)
                    val dataaa=data.toString()
                    val param = HashMap<String, String>()
                    param.put("Userid",Userid)
                    param.put("Jobtype",Decision)
                    param.put("Todaydate",dataaa)
                    return param
                }
                else{
                    val data= LocalDate.parse("2019-12-01",formatter)
                    val dataaa=data.toString()
                    val param = HashMap<String, String>()
                    param.put("Userid",Userid)
                    param.put("Jobtype",Decision)
                    param.put("Todaydate",dataaa)
                    return param  }}


        }

        VolleySingleton.instance?.addToRequestQueue(stringRequest)
        adapteris = Adapteris(context!!, Sarasas)
        tv_gruodis.adapter = adapteris

    }
    fun trinti(index: Int){
        Sarasas.removeAt(index)
        adapteris!!.notifyDataSetChanged()
    }
    inner class Adapteris : BaseAdapter{

        val c = Calendar.getInstance()
        var metai = c.get(Calendar.YEAR)
        var men = c.get(Calendar.MONTH)
        var diena = c.get(Calendar.DAY_OF_MONTH)
        var metaiiki = c.get(Calendar.YEAR)
        var meniki = c.get(Calendar.MONTH)
        var dienaiki = c.get(Calendar.DAY_OF_MONTH)

        var Sarasas = ArrayList<Darbai>()
        var context: Context? = null
        constructor(context: Context, Sarasas: ArrayList<Darbai>):super(){
            this.Sarasas = Sarasas
            this.context = context
        }

        override fun getCount(): Int {
            return Sarasas.size
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getItem(position: Int): Any {
            return Sarasas[position]
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            var nuo=""
            var iki=""
            val mypreference= SharedPrefManager(context!!)
            var Userid=mypreference.getID().toString()
            var Decision=mypreference.getDECISION().toString()
            val inflater = context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val eilutes = inflater.inflate(R.layout.activity_sarasas, null)
            var darbas = Sarasas[position]
            val Jobid=darbas.Jobid!!
            val Jobname=darbas.Darbai!!
            eilutes.textdienos.text = darbas.Diena!!
            eilutes.textdarbai.text = darbas.Darbai!!
            eilutes.imageView2.setImageResource(darbas.Foto!!)
            eilutes.imageView2.setOnClickListener {
                val dpd = DatePickerDialog(context, DatePickerDialog.OnDateSetListener{ view, year, month, dayOfMonth ->
                    metai = year
                    men = month
                    diena = dayOfMonth
                    Toast.makeText(context, "Nuo " + metai + " " + men + " " + diena, Toast.LENGTH_LONG).show()
                    val dpd2 = DatePickerDialog(context, DatePickerDialog.OnDateSetListener { viewiki, yeariki, monthiki, dayOfMonthiki ->
                        metaiiki = yeariki
                        meniki = monthiki
                        dienaiki = dayOfMonthiki
                        if (diena <= dienaiki && metai <= metaiiki && men <= meniki){
                            Toast.makeText(context, "Iki " + metaiiki + " " + meniki + " " + dienaiki, Toast.LENGTH_LONG).show()
                            val stringRequest = object : StringRequest(Request.Method.POST, EndPoints.URL_SEND_USER_JOBS,
                                Response.Listener<String> { s ->
                                    try {
                                         nuo = metai.toString()+"-"+men.toString()+"-"+diena.toString()
                                         iki = metaiiki.toString()+"-"+meniki.toString()+"-"+dienaiki.toString()
                                        val obj = JSONObject(s)
                                        Toast.makeText(context!!, obj.getString("message"), Toast.LENGTH_LONG).show()

                                        if(!obj.getBoolean("error")){
                                            var intent: Intent = Intent(context, ActivityKalendorius1::class.java)
                                            startActivity(intent)
                                            trinti(position)

                                        }



                                    } catch (e: JSONException) {
                                        e.printStackTrace()

                                    }
                                },
                                object : Response.ErrorListener {
                                    override fun onErrorResponse(volleyError: VolleyError) {
                                        Toast.makeText(context!!, volleyError.message, Toast.LENGTH_LONG).show()
                                    }
                                }) {
                                @Throws(AuthFailureError::class)
                                override fun getParams(): Map<String, String> {
                                    val params = HashMap<String, String>()
                                    params.put("Jobfrom", nuo)
                                    params.put("Jobto", iki)
                                    params.put("Userid",Userid)
                                    params.put("Jobid",Jobid.toString())
                                    params.put("Jobname",Jobname)
                                    params.put("Jobtype",Decision)
                                    return params
                                }

                            }

                            VolleySingleton.instance?.addToRequestQueue(stringRequest)
                            }
                        else {
                            Toast.makeText(context, "Neteisingai ivesti duomenys", Toast.LENGTH_LONG).show()
                        }
                    }, metaiiki, meniki, dienaiki)
                    dpd2.show()
                }, metai, men, diena)
                dpd.show()
            }
            return eilutes
        }
    }



}
