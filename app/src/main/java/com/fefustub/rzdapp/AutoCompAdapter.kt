package com.fefustub.rzdapp

import android.app.Activity
import android.graphics.Typeface
import android.text.SpannableString
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.TextView

class AutoCompAdapter(
    private val activity: Activity,
    private val resource: Int,
    array: ArrayList<String>,
    private val textEdit: AutoCompleteTextView
) :
    ArrayAdapter<String>(activity, resource, array) {
    var query: String = ""
    var inflater: LayoutInflater = LayoutInflater.from(activity)
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var itemView = convertView
        if (convertView == null) {
            itemView = inflater.inflate(resource, null)
        }
        val text = SpannableString(getItem(position))
        val ind = text.indexOf(query)
        if (ind != -1) {
            text.setSpan(StyleSpan(Typeface.BOLD), ind, ind + query.length, 0)
        }
        itemView!!.setOnClickListener {
            (activity as CreateIncidentActivity).onTypeSelected(
                1,
                getItem(position)
            )
        }
        itemView.findViewById<TextView>(R.id.suggestionText).text = text
        return itemView
    }

    override fun notifyDataSetChanged() {
        query = textEdit.text.toString()
        super.notifyDataSetChanged()
    }
}