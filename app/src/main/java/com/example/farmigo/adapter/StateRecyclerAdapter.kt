package com.example.farmigo.adapter

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.farmigo.R
import com.example.farmigo.util.MyGraphView
import com.example.farmigo.util.data
import com.example.farmigo.util.selectedMarkets
import kotlinx.android.synthetic.main.single_state.view.*

/*
    com.example.farmigo.adapter
    Created by:  Dhananjay Jhinkwan
    Date: 21-09-2020
*/

class StateRecyclerAdapter(
    val context: Context,
    val states: ArrayList<String>,
    val marketRecyclerView: RecyclerView,
    val structData: MutableMap<String, MutableMap<String, ArrayList<Pair<String, Double>>>>,
    val marketToPair: HashMap<String, ArrayList<Pair<String, Double>>>,
    val chart1: View,
) : RecyclerView.Adapter<StateRecyclerAdapter.StateViewHolder>() {

    var selectedStates = ArrayList<String>()

    class StateViewHolder(view : View) : RecyclerView.ViewHolder(view){
        val stateName = view.stateCard.state_name
        val stateLayout = view.stateLayout
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StateViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.single_state,parent,false)
        return StateViewHolder(view)
    }

    override fun onBindViewHolder(holder: StateViewHolder, position: Int) {
        holder.stateName.text = states[position]
        holder.stateLayout.setOnClickListener{
            var currColor = (it.background as ColorDrawable).color
            if(currColor == Color.parseColor("#ffffff")) {
                it.setBackgroundColor(Color.parseColor("#a0284e"))
                it.state_name.setTextColor(Color.parseColor("#ffffff"))
                selectedStates.add(states[position])
            }
            else {
                    it.setBackgroundColor(Color.parseColor("#ffffff"))
                    it.state_name.setTextColor(Color.parseColor("#000000"))

                    for(i in structData[states[position]]!!){
                        if(selectedMarkets[i.key]!=null) {
                            Log.e(
                                "Should delete",
                                "${Pair(selectedMarkets[i.key]!!, marketToPair[i.key])}"
                            )
                            data.remove(Pair(selectedMarkets[i.key]!!, marketToPair[i.key]))
                        }
                        selectedMarkets[i.key]= Color.WHITE
                        MyGraphView(chart1).plotGraph()
                    }

                    selectedStates.remove(states[position])
            }
            val markets = ArrayList<String>()
            for(i in 0 until selectedStates.size){
                for(j in structData[selectedStates[i]]!!){
                    markets.add(j.key)
                }
            }

            Log.e("Market to pair State","$marketToPair")
            val adapter = MarketRecyclerAdapter(context,markets,structData,marketToPair,chart1)
            marketRecyclerView.adapter = adapter
            marketRecyclerView.layoutManager = StaggeredGridLayoutManager(2,RecyclerView.HORIZONTAL)
//            Toast.makeText(context,holder.stateName.text.toString(),Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int {
        return states.size
    }

}