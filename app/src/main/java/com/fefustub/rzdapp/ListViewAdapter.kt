import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.text.style.BackgroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.core.text.clearSpans
import com.fefustub.rzdapp.datatypes.Incident
import com.fefustub.rzdapp.IncidentViewActivity
import com.fefustub.rzdapp.R
import java.text.SimpleDateFormat
import java.util.*

class ListViewAdapter(
    private var activity: Activity,
    private val items: List<Incident>,
) :
    BaseAdapter() {
    var inflater: LayoutInflater = LayoutInflater.from(activity)
    private val list = ArrayList<Incident>()
    private var isSearching = false

    override fun getCount(): Int {
        return list.size
    }

    override fun getItem(position: Int): Incident {
        return list[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        var itemView = view
        val item = list[position]
        val searchView =
            if (itemView == null) false else (itemView.findViewById<TextView>(R.id.statusTextView) == null)
        if (itemView == null || ((isSearching) && !searchView) || (!isSearching) && searchView) {
            itemView = inflater.inflate(
                if (isSearching) R.layout.suggestion_view_card else R.layout.incident_view_card,
                null
            )
        }
        itemView!!.findViewById<TextView>(R.id.descriptionTextView).text =
            if (item.DESCRIPTION != null) item.searchableDesc else activity.getString(R.string.not_defined)
        itemView.setOnClickListener {
            val intent = Intent(activity, IncidentViewActivity::class.java)
            intent.putExtra("incident", position)
            activity.startActivity(intent)
        }
        if (isSearching) {
            return itemView
        }
        itemView.findViewById<TextView>(R.id.statusTextView).text =
            if (item.STATUS != null) item.STATUS else activity.getString(R.string.not_defined)
        itemView.findViewById<TextView>(R.id.systemTextView).text =
            if (item.EXTSYSNAME != null) item.EXTSYSNAME else activity.getString(R.string.not_defined)
        itemView.findViewById<TextView>(R.id.foundDateTextView).text =
            if (item.ERRORDATE != null) SimpleDateFormat(
                "yyyy-MM-dd HH:mm",
                Locale.getDefault()
            ).format(item.ERRORDATE!!) else activity.getString(R.string.not_defined)
        itemView.findViewById<TextView>(R.id.deadlineTextView).text =
            if (item.TARGETFINISHDATE != null) SimpleDateFormat(
                "yyyy-MM-dd HH:mm",
                Locale.getDefault()
            ).format(item.TARGETFINISHDATE!!) else activity.getString(R.string.not_defined)
        return itemView
    }

    fun filter(charText: String) {
        var text = charText
        text = text.lowercase(Locale.getDefault())
        list.clear()
        if (text.isNotEmpty()) {
            isSearching = true;
            for (wp in items) {
                if (wp.DESCRIPTION == null) continue
                val ind = wp.DESCRIPTION!!.lowercase(Locale.getDefault()).indexOf(text)
                if (ind != -1) {
                    wp.searchableDesc.clearSpans()
                    wp.searchableDesc.setSpan(
                        BackgroundColorSpan(Color.YELLOW),
                        ind,
                        ind + text.length,
                        0
                    )
                    list.add(wp)
                } else {
                    wp.searchableDesc.clearSpans()
                }
            }
        } else {
            isSearching = false;
        }
        if (list.isEmpty()) {
            if (text.isEmpty()) {
                list.addAll(items)
                list.forEach { i -> i.searchableDesc.clearSpans() }
            }
        }
        notifyDataSetChanged()
    }
}