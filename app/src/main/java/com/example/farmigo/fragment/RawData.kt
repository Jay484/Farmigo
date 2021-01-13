package com.example.farmigo.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.farmigo.R
import com.example.farmigo.adapter.StateRecyclerAdapter
import com.example.farmigo.util.MyGraphView
import kotlinx.android.synthetic.main.fragment_raw_data.view.*
import kotlinx.android.synthetic.main.linechart.view.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [RawData.newInstance] factory method to
 * create an instance of this fragment.
 */
class RawData(
    val structData: MutableMap<String, MutableMap<String, ArrayList<Pair<String, Double>>>>,
    val marketToPair: HashMap<String, ArrayList<Pair<String, Double>>>
) : Fragment() {

    private lateinit var stateAdapter: StateRecyclerAdapter
    private var states = ArrayList<String>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_raw_data, container, false)

        view.chart1.setNoDataText(context?.getString(R.string.emptyChart))

        var states = ArrayList<String>()
        for(i in structData){
            states.add(i.key)
        }
        Log.e("Passed",states.toString())
        Log.e("Market to pair raw","${marketToPair}")

//        MyGraphView(view).plotGraph()

        stateAdapter = StateRecyclerAdapter(activity as Context,states,view.market_recycler_view,structData,marketToPair,view)
        view.state_recycler_view.adapter = stateAdapter
        view.state_recycler_view.layoutManager = StaggeredGridLayoutManager(1,RecyclerView.HORIZONTAL)

        return view
    }
}