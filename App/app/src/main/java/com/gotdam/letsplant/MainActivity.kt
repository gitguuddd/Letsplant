package com.gotdam.letsplant

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import kotlinx.android.synthetic.main.activity_main.*
import android.security.NetworkSecurityPolicy
import org.json.JSONException
import org.json.JSONObject

class


MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        login.setOnClickListener {


            val name = username.text.toString()
            val Password = password.text.toString()
            var err = false
            if (name.isNullOrBlank()) {
                Toast.makeText(applicationContext, "Įveskite vartotojo vardą", Toast.LENGTH_LONG).show()
                err = true
            }
            if (Password.isNullOrBlank()) {
                Toast.makeText(applicationContext, "Įveskite slaptažodį", Toast.LENGTH_LONG).show()
                err = true
            }

            if(err==false){
            val stringRequest = object : StringRequest(Request.Method.POST, EndPoints.URL_LOGIN,
                Response.Listener<String> { s ->
                    try {
                        val obj = JSONObject(s)
                        Toast.makeText(applicationContext, obj.getString("message"), Toast.LENGTH_LONG).show()
                        val mypreference= SharedPrefManager(this)
                        val ID=obj.getInt("ID")
                        mypreference.setID(ID)
                        if(!obj.getBoolean("error")){
                            val intent: Intent = Intent(applicationContext, ActivityPasirinkimas::class.java)
                            startActivity(intent)
                        }



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
                    override fun getParams(): Map<String, String> {
                        val params = HashMap<String, String>()
                        params.put("name", name)
                        params.put("Password", Password)
                        return params
                    }

                }

                VolleySingleton.instance?.addToRequestQueue(stringRequest)


            }
        }
        register.setOnClickListener {
            val intent: Intent = Intent(applicationContext, ActivityRegistracija::class.java)
            startActivity(intent)
        }

    }
}
