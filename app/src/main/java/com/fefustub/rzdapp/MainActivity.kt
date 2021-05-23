package com.fefustub.rzdapp

import ListViewAdapter
import android.Manifest
import android.app.SearchManager
import android.content.Context
import android.content.pm.PackageManager
import android.database.Cursor
import android.database.MatrixCursor
import android.os.Build
import android.os.Bundle
import android.provider.BaseColumns
import android.view.Menu
import android.widget.SearchView
import android.widget.SimpleCursorAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.MenuItemCompat
import com.fefustub.rzdapp.common.OnFragmentFinish
import java.util.*


class MainActivity : AppCompatActivity(), OnFragmentFinish, SearchView.OnQueryTextListener {
    private val REQUEST_ID_READ_PERMISSION = 100
    private lateinit var searchView: SearchView
    private lateinit var adapter: ListViewAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        askReadFilePermission()
        IncidentController.parse()
        adapter = ListViewAdapter(this,IncidentController.incidentList!!)
        (supportFragmentManager.fragments[0] as ListViewFragment).adapter = adapter
        adapter.filter("")
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)

        // Associate searchable configuration with the SearchView
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView = menu.findItem(R.id.search).actionView as SearchView
        (searchView).apply {
            setSearchableInfo(searchManager.getSearchableInfo(componentName))
            queryHint = getString(R.string.search_hint)
        }
        searchView.setOnQueryTextListener(this)
        return true
    }

    private fun askReadFilePermission() {
        val canRead = askPermission(
            REQUEST_ID_READ_PERMISSION,
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) Manifest.permission.MANAGE_EXTERNAL_STORAGE else Manifest.permission.READ_EXTERNAL_STORAGE
        )
        if (!canRead) {
            return
        }
    }

    private fun askPermission(requestId: Int, permissionName: String): Boolean {
        val permission = ActivityCompat.checkSelfPermission(this, permissionName)
        if (permission != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(
                arrayOf(permissionName),
                requestId
            )
            return false
        }
        return true
    }

    override fun onFinish(status: Int?, data: Any?) {
        TODO("Not yet implemented")
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        adapter.filter(newText!!)
        return false
    }
}