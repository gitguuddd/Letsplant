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
import kotlinx.android.synthetic.main.activity_register.*
import org.json.JSONException
import org.json.JSONObject

class ActivityRegistracija : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        register.setOnClickListener {
            val name = username.text.toString()
            val Password = password.text.toString()
            val Email = email.text.toString()
            var err = false
            if (name.isNullOrBlank()) {
                Toast.makeText(applicationContext, "Įveskite vartotojo vardą", Toast.LENGTH_SHORT).show()
                err = true
            }
            if (Password.isNullOrBlank()) {
                Toast.makeText(applicationContext, "Įveskite slaptažodį", Toast.LENGTH_SHORT).show()
                err = true
            }
            if (Email.isNullOrBlank()) {
                Toast.makeText(applicationContext, "Įveskite vartotojo paštą", Toast.LENGTH_SHORT).show()
                err = true
            }
            if (name is String && name.length <= 3 && err == false) {
                Toast.makeText(
                    applicationContext,
                    "Vartotojo vardas turi būti ilgesnis nei 3 simboliai",
                    Toast.LENGTH_SHORT
                ).show()
                err = true
            }
            if (Password is String && Password.length <= 3 && err == false) {
                Toast.makeText(applicationContext, "Slaptažodis turi būti ilgesnis nei 3 simboliai", Toast.LENGTH_SHORT)
                    .show()
                err = true
            }
            if (Email is String && Email.contains('@') != true && err == false) {
                Toast.makeText(
                    applicationContext,
                    "Įvestas paštas neatitinka standartinio pašto formato",
                    Toast.LENGTH_SHORT
                ).show()
                err = true
            }

            if (!err) {
                //creating volley string request
                val stringRequest = object : StringRequest(Request.Method.POST, EndPoints.URL_ADD_USER,
                    Response.Listener<String> { response ->
                        try {
                            val obj = JSONObject(response)
                            Toast.makeText(applicationContext, obj.getString("message"), Toast.LENGTH_LONG).show()

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
                        params.put("Email", Email)
                        return params
                    }
                }
                //adding request to queue
                VolleySingleton.instance?.addToRequestQueue(stringRequest)

                val intent: Intent = Intent(applicationContext, MainActivity::class.java)
                startActivity(intent)
            }
        }
    }
}
