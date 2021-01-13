package com.example.farmigo.activity

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.farmigo.R
import com.example.farmigo.adapter.RawDataRecyclerAdapter
import com.example.farmigo.databinding.ActivityMainBinding
import com.example.farmigo.fragment.RawData
import com.example.farmigo.util.ReadData
import com.example.farmigo.util.data
import com.example.farmigo.util.selectedMarkets
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class MainActivity : AppCompatActivity() {

    lateinit var rawDataRecyclerAdapter: RawDataRecyclerAdapter
    lateinit var layoutManager: RecyclerView.LayoutManager
    lateinit var currLang : String

    //    lateinit var textView : TextView
    lateinit var dataSet: ArrayList<Any>
    val structData = HashMap<String, MutableMap<String, ArrayList<Pair<String, Double>>>>()
        .withDefault {
            HashMap<String, ArrayList<Pair<String, Double>>>().withDefault {
                ArrayList()
            }
        }
    var marketToPair = HashMap<String, ArrayList<Pair<String, Double>>>()
    private lateinit var binding: ActivityMainBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        currLang = intent?.getStringExtra("Locale")!!
        setLocale(currLang)
        Log.e("Lnaguage selected ", intent?.getStringExtra("Locale") ?: " ")

        selectedMarkets = HashMap<String, Int>()

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        initializeData()
    }

    override fun onRestart() {
        super.onRestart()
        Log.e("Main activity", "Activity restarted ")
    }

    override fun onDestroy() {
        selectedMarkets.clear()
        data = ArrayList()
        super.onDestroy()
    }

    private fun initializeData() {

//        binding.mainLayout.visibility = View.GONE
//        textView = binding.data

        setSupportActionBar(binding.toolBar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        layoutManager = LinearLayoutManager(this)
        GlobalScope.launch {
            withContext(Dispatchers.IO) {
                dataSet = ReadData().readExcel(this@MainActivity)

                val dataSet2 = dataSet as ArrayList<ArrayList<String>>
                for (i in 1 until dataSet.size) {
                    val pair =
                        Pair(dataSet2[i][2], dataSet2[i][3].toDouble())  //pair of month and price

                    if (structData[dataSet2[i][0]] == null) {   //empty state
                        structData[dataSet2[i][0]] = HashMap()
                    }
                    if (structData[dataSet2[i][0]]!![dataSet2[i][1]] == null) {   //empty market
                        structData[dataSet2[i][0]]!![dataSet2[i][1]] = ArrayList()
                        marketToPair[dataSet2[i][1]] = ArrayList()

                    }
                    structData[dataSet2[i][0]]!![dataSet2[i][1]]?.add(pair)
                    marketToPair[dataSet2[i][1]]!!.add(pair)

                }

                Log.e("data", structData.toString())
            }
            withContext(Dispatchers.Main) {
                try {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.mianFragment, RawData(structData, marketToPair)).commit()
                }catch (exp : Exception){}
            }
        }
        toggel_button.setOnClickListener {
            AlertDialog.Builder(this).apply {
                setTitle(getString(R.string.lang_select_titl))
                setMessage(getString(R.string.lang_select)+"\n"+ getString(R.string.lose_data))
                setNegativeButton(getString(R.string.Hindi)) { _, _ ->
                    if(currLang.compareTo("hi") != 0)
                        restartActivity("hi")
                }
                setPositiveButton(getString(R.string.English)) { _, _ ->
                    if(currLang.compareTo("en")!=0)
                        restartActivity("en")
                }
                create()
                show()
            }
        }
    }

    fun setLocale(localeCode: String) {
        val languageToLoad = localeCode // your language

        val locale = Locale(languageToLoad)
        Locale.setDefault(locale)
        val config = Configuration()
        config.setLocale(locale)
        baseContext.resources.updateConfiguration(
            config,
            baseContext.resources.displayMetrics
        )
    }

    fun restartActivity(localeCode: String){
        startActivity(Intent(this@MainActivity, MainActivity::class.java).apply {
            putExtra("Locale", localeCode)
        })
        finish()
    }

}