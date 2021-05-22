import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.fefustub.rzdapp.Incident
import com.fefustub.rzdapp.IncidentViewActivity
import com.fefustub.rzdapp.R
import com.fefustub.rzdapp.common.OnFragmentFinish
import java.text.SimpleDateFormat
import java.util.*

class ListViewAdapter(
    private var activity: Activity,
    private val items: List<Incident>,
    private val onFinishListener: OnFragmentFinish
) :
    BaseAdapter() {
    var inflater: LayoutInflater = LayoutInflater.from(activity)

    override fun getCount(): Int {
        return items.size
    }

    override fun getItem(position: Int): Incident {
        return items[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        var itemView = view
        val item = items[position]
        if (itemView == null) {
            itemView = inflater.inflate(R.layout.incident_view_card, null)
        }
        itemView!!.findViewById<TextView>(R.id.statusTextView).text =
            if (item.STATUS != null) item.STATUS else activity.getString(R.string.not_defined)
        itemView.findViewById<TextView>(R.id.descriptionTextView).text =
            if (item.DESCRIPTION != null) item.DESCRIPTION else activity.getString(R.string.not_defined)
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
        itemView.setOnClickListener {
            val intent = Intent(activity,IncidentViewActivity::class.java)
            intent.putExtra("incident",position)
            activity.startActivity(intent)
        }
        return itemView
    }
}