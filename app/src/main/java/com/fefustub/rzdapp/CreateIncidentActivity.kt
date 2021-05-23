package com.fefustub.rzdapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AutoCompleteTextView
import android.widget.Toast
import com.fefustub.rzdapp.datatypes.FaultType
import com.fefustub.rzdapp.datatypes.Incident
import com.fefustub.rzdapp.datatypes.Specification
import java.util.*
import kotlin.collections.ArrayList

class CreateIncidentActivity : AppCompatActivity() {
    private var spec: Specification? = null
    private var fault: FaultType? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_incident)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_baseline_close_24)
        title = getString(R.string.create_incident_title)
        val incTypeText = findViewById<AutoCompleteTextView>(R.id.incTypeTextEdit)
        val faults: ArrayList<String> = arrayListOf()
        IncidentController.faultTypes!!.forEach {
            if (it.DESCRIPTION != null) {
                faults.add(it.DESCRIPTION!!)
            }
        }
        val adapter = AutoCompAdapter(
            this, R.layout.suggestion_autocomp_view,
            faults,
            incTypeText
        )
        incTypeText.setAdapter(adapter)
        val normtext = findViewById<AutoCompleteTextView>(R.id.normTextEdit)
        normtext.setOnFocusChangeListener { v: View, hasFocus: Boolean ->
            if (spec != null) {
                if (!hasFocus) {
                    if (normtext.text != null) {
                        if (normtext.text.toString().isNotEmpty()) {
                            val num = normtext.text.toString().toDouble()
                            if (spec!!.MAX != null) {
                                if (num > spec!!.MAX!!.toDouble()) {
                                    normtext.setText(spec!!.MAX)
                                }
                            }
                            if (spec!!.MIN != null) {
                                if (num > spec!!.MIN!!.toDouble()) {
                                    normtext.setText(spec!!.MIN)
                                }
                            }
                        }
                    }

                }
            }
        }
        findViewById<AutoCompleteTextView>(R.id.deadlineTextEdit).setOnClickListener {

        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
            R.id.check -> {
                if (fault != null) {
                    val inc = Incident(
                        EXTSYSNAME = "MPM",
                        DESCRIPTION = fault!!.DESCRIPTION,
                        TICKETID = "0000000000", //12
                        STATUS = "НОВОЕ",
                        CLASSIDMAIN = spec!!.CLASSSTRUCTUREID,
                        REPORTEDBY = "Тест",
                        CRITIC_LEVEL = spec!!.CRITIC_LEVEL,
                        NORM = findViewById<AutoCompleteTextView>(R.id.normTextEdit).text.toString(),
                        LNORM = findViewById<AutoCompleteTextView>(R.id.lnormTextEdit).text.toString()
                    )
                    val time = Calendar.getInstance()
                    inc.ERRORDATE = time.time
                    time.add(Calendar.DAY_OF_YEAR, spec!!.ENDDATE!!.toInt())
                    inc.TARGETFINISHDATE = time.time
                    IncidentController.incidentList!!.add(inc)
                    Toast.makeText(this, "Сохранено успешно", Toast.LENGTH_LONG).show()
                    onBackPressed()
                } else {
                    Toast.makeText(this, getString(R.string.save_failure), Toast.LENGTH_LONG).show()
                }
            }
            else -> super.onOptionsItemSelected(item)
        }
        return false
    }

    fun onTypeSelected(type: Int, data: Any?) {
        when (type) {
            1 -> {
                fault =
                    IncidentController.faultTypes!!.find { i -> i.DESCRIPTION.equals(data as String) }!!
                spec =
                    IncidentController.specsList!!.find { i -> i.CLASSSTRUCTUREID.equals(fault!!.CLASSSTRUCTUREID) }
                findViewById<AutoCompleteTextView>(R.id.incTypeTextEdit).setText(fault!!.DESCRIPTION)
            } //FaultType
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.options_menu_accept, menu)
        return true
    }
}