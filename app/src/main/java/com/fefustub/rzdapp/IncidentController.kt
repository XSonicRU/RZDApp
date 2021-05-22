package com.fefustub.rzdapp

import android.os.Environment
import com.beust.klaxon.Klaxon
import org.json.JSONArray
import org.json.JSONObject
import java.io.File

object IncidentController {
    var incidentList: List<Incident>? = null

    fun parse() {
        incidentList = Klaxon().parseArray(
            File(
                Environment.getExternalStorageDirectory().absolutePath, "incidents.json"
            )
        )
    }
}