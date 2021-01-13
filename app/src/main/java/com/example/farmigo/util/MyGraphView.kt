package com.example.farmigo.util

import android.graphics.Color
import android.util.Log
import android.view.View
import com.example.farmigo.R
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.renderer.YAxisRenderer
import kotlinx.android.synthetic.main.linechart.view.*
import org.apache.poi.ss.usermodel.charts.AxisPosition


/*
    com.example.farmigo.util
    Created by:  Dhananjay Jhinkwan
    Date: 21-09-2020
*/

class MyGraphView(val graph: View) {
    fun plotGraph() {

        Log.e("called", "Function called")

        val map = hashMapOf(
            Pair(1, "0"), Pair(2, "Jan"), Pair(3, "Feb"), Pair(4, "Mar"), Pair(5, "Apr"), Pair(6,"May"),
            Pair(7, "Jun"), Pair(8, "Jul"), Pair(9, "Aug"), Pair(10, "Sep"), Pair(11, "Oct"), Pair(12,"Nov"), Pair(13, "Dec")
        )


        val monthToIndex = HashMap<String, Int>()

        for (i in map) {
            monthToIndex[i.value] = i.key
        }

        var index = 0;
        val lineDataSets = ArrayList<LineDataSet>()
        for (i in data) {
            val entries = ArrayList<Entry>()
            Log.e("Index", "Line ")
            for (j in i.second) {
                Log.e("Data point", "${monthToIndex[j.first]?.toFloat()}   ${j.second.toFloat()} ")
                entries.add(Entry(monthToIndex[j.first]!!.toFloat(), j.second.toFloat()))
            }
            val dataset = LineDataSet(entries, null)
            dataset.color = i.first
            dataset.lineWidth = 5.toFloat()
            index++
            lineDataSets.add(dataset)
        }


        graph.chart1.apply {
            description.isEnabled = false

            animateX(800)



            xAxis.valueFormatter =object :  ValueFormatter(){
                override fun getFormattedValue(value: Float): String {
                    return if(value - value.toInt().toFloat() == 0.toFloat()){
                        when(map[value.toInt()]){
                            "Jan" -> context.getString(R.string.Jan)
                            "Feb" -> context.getString(R.string.Feb)
                            "Mar" -> context.getString(R.string.Mar)
                            "Apr" -> context.getString(R.string.Apr)
                            "May" -> context.getString(R.string.May)
                            "Jun" -> context.getString(R.string.Jun)
                            "Jul" -> context.getString(R.string.Jul)
                            "Aug" -> context.getString(R.string.Aug)
                            "Sep" -> context.getString(R.string.Sep)
                            "Oct" -> context.getString(R.string.Oct)
                            "Nov" -> context.getString(R.string.Nov)
                            "Dec" -> context.getString(R.string.Dec)
                            else -> " "
                        }
                    }
                    else
                        " "
                }

            }

            axisRight.setDrawLabels(false)
            axisRight.setDrawAxisLine(false)

            axisLeft.textColor = Color.WHITE
            axisLeft.textSize = 10f
            axisLeft.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART)
            axisLeft.axisLineColor = Color.BLUE

            xAxis.position = XAxis.XAxisPosition.BOTTOM_INSIDE
            xAxis.setDrawGridLines(false)
            xAxis.textColor = Color.WHITE
            xAxis.textSize = 10f
            xAxis.axisLineColor = Color.BLUE

            data = LineData(lineDataSets.toList())

            invalidate()
        }
    }
}