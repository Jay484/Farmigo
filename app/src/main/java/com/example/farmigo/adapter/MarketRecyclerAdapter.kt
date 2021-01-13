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
import com.example.farmigo.R
import com.example.farmigo.util.MyGraphView
import com.example.farmigo.util.data
import com.example.farmigo.util.selectedMarkets
import kotlinx.android.synthetic.main.single_market.view.*
import yuku.ambilwarna.AmbilWarnaDialog
import yuku.ambilwarna.AmbilWarnaDialog.OnAmbilWarnaListener

/*
    com.example.farmigo.adapter
    Created by:  Dhananjay Jhinkwan
    Date: 21-09-2020
*/

class MarketRecyclerAdapter(
    val context: Context,
    val markets: ArrayList<String>,
    val structData: MutableMap<String, MutableMap<String, ArrayList<Pair<String, Double>>>>,
    val marketToPair: HashMap<String, ArrayList<Pair<String, Double>>>,
    val chart1: View
)
        : RecyclerView.Adapter<MarketRecyclerAdapter.MarketViewHolder>() {

    var marketArray = ArrayList<String>()
    class MarketViewHolder(view : View) : RecyclerView.ViewHolder(view){
        val market_name = view.market_name
        val market_layout = view.marketLayout
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarketViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.single_market,parent,false)
        for(i in selectedMarkets){
            Log.e("added","on market viewholder create")
            if(selectedMarkets[i.key] != Color.WHITE)
                if(!data.contains(Pair(i.value,marketToPair[i.key]!!)))
                  data.add(Pair(i.value,marketToPair[i.key]!!))
        }
//        MyGraphView(chart1).plotGraph()
        return MarketViewHolder(view)
    }

    override fun onBindViewHolder(holder: MarketViewHolder, position: Int) {
        holder.market_name.text = markets[position]

        if(selectedMarkets[markets[position]] != null && selectedMarkets[markets[position]] != Color.WHITE){
            holder.market_layout.setBackgroundColor(selectedMarkets[markets[position]]!!)
        }

        holder.market_layout.setOnClickListener{
            var currColor = (it.background as ColorDrawable).color
            if(currColor == Color.parseColor("#ffffff")){
                AmbilWarnaDialog(context,Color.parseColor("#ffffff"),object : OnAmbilWarnaListener{
                    override fun onCancel(dialog: AmbilWarnaDialog?) {

                    }

                    override fun onOk(dialog: AmbilWarnaDialog?, color: Int) {
                        if(color == Color.WHITE)
                            return
                        marketArray.add(it.market_name.text.toString())
                        it.setBackgroundColor(color)
                        selectedMarkets[it.market_name.text.toString()] = color
//                        Toast.makeText(context,"${it.market_name.text}",Toast.LENGTH_SHORT).show()
                        data.add(Pair(color,marketToPair[it.market_name.text.toString()]!!))

                        Log.e("Selected","$data")
                        MyGraphView(chart1).plotGraph()
                    }

                }).show()
            }
            else{
                Log.e("should delete","${Pair((it.background as ColorDrawable).color,marketToPair[it.market_name.text.toString()])}")
                data.remove(Pair((it.background as ColorDrawable).color,marketToPair[it.market_name.text.toString()]))
                it.setBackgroundColor(Color.parseColor("#ffffff"))
                marketArray.remove(it.market_name.text.toString())
                MyGraphView(chart1).plotGraph()
                Log.e("Selected","$data")
                selectedMarkets[it.market_name.text.toString()] = Color.WHITE
            }
        }
    }

    override fun getItemCount(): Int {
        return markets.size
    }
}