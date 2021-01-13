package com.example.farmigo.util

import android.content.Context
import android.util.Log
import android.widget.TextView
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.poifs.filesystem.POIFSFileSystem
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.lang.Exception

/*
    com.example.farmigo.util
    Created by:  Dhananjay Jhinkwan
    Date: 21-09-2020
*/

class ReadData {

    var headings = ArrayList<String>()
    var dataBody = ArrayList<Any>()

    var price = ArrayList<Int>()
    suspend fun readExcel(context: Context) : ArrayList<Any>{
        try {
            val data = context.assets.open("data.xlsx")
            var workbook = XSSFWorkbook(data)

            val sheet = workbook.getSheet("Onion 2019 Monthly Prices")
            var rowIterator = sheet.rowIterator()

            //Initialize headings
            var row = rowIterator.next()
            val cellIterator = row.cellIterator()
            while (cellIterator.hasNext()){
                val cell = cellIterator.next()
                headings.add(cell.toString())
            }
            dataBody.add(headings)
            //Fill data

            while(rowIterator.hasNext()){
                row = rowIterator.next()
                var rowdata = ArrayList<String>()
                val cellIterator = row.cellIterator()
                while(cellIterator.hasNext()){
                    val cell = cellIterator.next()
                    rowdata.add(cell.toString())
                }
                dataBody.add(rowdata)
            }

        }
        catch (exp: Exception){
            Log.e("Exception","${exp.message} occured")
        }
        return dataBody
    }
}