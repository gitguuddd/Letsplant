package com.gotdam.letsplant



object EndPoints {
    private val URL_ROOT = "http://lpdt.us.lt/?op="
    val URL_ADD_USER = URL_ROOT + "AddUser"
    val URL_LOGIN =URL_ROOT + "Login"
    val URL_FECTH_JOBS =URL_ROOT + "FetchJobs"
    val URL_SEND_USER_JOBS=URL_ROOT + "SendUserJobs"
    val URL_FECTH_CALENDAR_JOBS=URL_ROOT+"FetchCalendarJobs"
}
