package com.fefustub.rzdapp

import ListViewAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.Fragment
import com.fefustub.rzdapp.common.OnFragmentFinish


class ListViewFragment : Fragment(), OnFragmentFinish {
    lateinit var adapter: ListViewAdapter
    private lateinit var onFinishListener: OnFragmentFinish
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        onFinishListener = requireActivity() as OnFragmentFinish
        return inflater.inflate(R.layout.fragment_listview, container, false)
    }

    override fun onStart() {
        super.onStart()
        val view = requireView()
        view.findViewById<ListView>(R.id.visitview).adapter = adapter
    }

    override fun onFinish(status: Int?, data: Any?) {

    }

}