package com.fefustub.rzdapp

import android.os.Environment
import com.beust.klaxon.Klaxon
import com.fefustub.rzdapp.datatypes.FaultType
import com.fefustub.rzdapp.datatypes.Incident
import com.fefustub.rzdapp.datatypes.Specification
import java.io.File

object IncidentController {
    var incidentList: MutableList<Incident>? = null
    var faultTypes: List<FaultType>? = null
    var specsList: List<Specification>? = null
    fun parse() {
        incidentList = Klaxon().parseArray<Incident>(
            File(
                Environment.getExternalStorageDirectory().absolutePath, "incidents.json"
            )
        )!!.toMutableList()
        faultTypes = Klaxon().parseArray(
            File(
                Environment.getExternalStorageDirectory().absolutePath, "typesFaults.json"
            )
        )
        specsList = Klaxon().parseArray(
            File(
                Environment.getExternalStorageDirectory().absolutePath, "specificationsInc.json"
            )
        )
    }
}