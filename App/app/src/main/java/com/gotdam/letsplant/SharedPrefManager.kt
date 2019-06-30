package com.gotdam.letsplant
import android.content.Context
class SharedPrefManager (context : Context){
    val PREFERENCE_NAME = "letsplant"
    val PREFERENCE_ID ="ID"
    val PREFERENCE_DECISION= "DECISION"
    val preference = context.getSharedPreferences(PREFERENCE_NAME,Context.MODE_PRIVATE)

    fun getID() : Int{
        return preference.getInt(PREFERENCE_ID,0)
    }
    fun setID(ID : Int){
        val editor = preference.edit()
        editor.putInt(PREFERENCE_ID,ID)
        editor.apply()
    }
    fun getDECISION() : String{
        return preference.getString(PREFERENCE_DECISION,"")
    }
    fun setDECISION(DECISION : String){
        val editor = preference.edit()
        editor.putString(PREFERENCE_DECISION,DECISION)
        editor.apply()
    }

}