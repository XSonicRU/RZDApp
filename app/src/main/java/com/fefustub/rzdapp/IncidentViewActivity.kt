package com.fefustub.rzdapp

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.*

class IncidentViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.incident_view)
        val incident = IncidentController.incidentList!![intent.getIntExtra("incident", -1)]
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        title = getString(R.string.incident_view_label)
        findViewById<TextView>(R.id.descTextView).text =
            getString(R.string.desc_text_pl, incident.DESCRIPTION, incident.TICKETID)
        findViewById<TextView>(R.id.regTextView).text = incident.REPORTEDBY
        findViewById<TextView>(R.id.critlevelTextView).text = incident.CRITIC_LEVEL
        findViewById<TextView>(R.id.foundTextView).text = SimpleDateFormat(
            "yyyy-MM-dd HH:mm",
            Locale.getDefault()
        ).format(incident.ERRORDATE!!)
        findViewById<TextView>(R.id.deadlineTextView).text = SimpleDateFormat(
            "yyyy-MM-dd HH:mm",
            Locale.getDefault()
        ).format(incident.TARGETFINISHDATE!!)
        findViewById<TextView>(R.id.systemTextView).text = incident.EXTSYSNAME
        findViewById<TextView>(R.id.statusTextView).text = incident.STATUS
        findViewById<TextView>(R.id.normTextView).text = incident.NORM
        findViewById<TextView>(R.id.lnormTextView).text = incident.LNORM
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
        return false
    }
}