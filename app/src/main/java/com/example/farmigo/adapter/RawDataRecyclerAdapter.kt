package com.example.farmigo.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.farmigo.R
import kotlinx.android.synthetic.main.single_row.view.*
import org.apache.poi.xslf.usermodel.Placeholder

/*
    com.example.farmigo.adapter
    Created by:  Dhananjay Jhinkwan
    Date: 21-09-2020
*/

class RawDataRecyclerAdapter(val context: Context, val data : ArrayList<Any>) : RecyclerView.Adapter<RawDataRecyclerAdapter.RawDataRecyclerAdapterViewHolder>() {

    class RawDataRecyclerAdapterViewHolder(view : View) : RecyclerView.ViewHolder(view){
        val state = view.txt_state
        val apmc = view.txt_apmc
        val month = view.txt_month
        val price = view.txt_price
        val sigleCard = view.singleCard
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RawDataRecyclerAdapterViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.single_row,parent,false)

        return RawDataRecyclerAdapterViewHolder(view)
    }

    override fun onBindViewHolder(holder: RawDataRecyclerAdapterViewHolder, position: Int) {
        val currRow = data[position] as ArrayList<*>
        holder.state.text = currRow[0].toString()
        holder.apmc.text = currRow[1].toString()
        holder.month.text = currRow[2].toString()
        holder.price.text = currRow[3].toString()
        holder.sigleCard.setOnClickListener{
            Toast.makeText(context,"${currRow[1]}",Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

}