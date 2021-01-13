package com.example.farmigo.activity

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AlertDialog
import com.example.farmigo.R

class Configuration : AppCompatActivity() {
    private lateinit var sharedPreferences : SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_configuration)
        sharedPreferences = getSharedPreferences("MyPreferences", MODE_PRIVATE)

        val isConfigured = sharedPreferences.getBoolean("isConfig",false)
        if(!isConfigured){
            AlertDialog.Builder(this).apply {
                title = getString(R.string.lang_select_titl)
                setMessage(getString(R.string.lang_select))
                setPositiveButton(getString(R.string.Hindi)) { _, _ ->
                    startNextActivity("hi")
                }
                setNegativeButton(getString(R.string.English)){ _,_ ->
                    startNextActivity("en")
                }
                setCancelable(false)
                create()
                show()
            }
        }
        else{
            startNextActivity(sharedPreferences.getString("Lang","en"))
        }
    }

    fun startNextActivity(str: String?){
        sharedPreferences.edit().putBoolean("isConfig",true).apply()
        var lang ="en"
        if(str != null){
            lang = str
        }
        startActivity(Intent(this, MainActivity::class.java).apply {
            putExtra("Locale", sharedPreferences.getString("Lang", lang))
            finish()
        })
    }
}