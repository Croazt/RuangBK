package com.example.proyekakhirpsi

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.proyekakhirpsi.config.StoreManager
import com.example.proyekakhirpsi.models.JanjiList
import kotlinx.android.synthetic.main.detail.*
import org.w3c.dom.Text
import java.text.SimpleDateFormat
import java.util.*

class DetailRiwayatActivity : AppCompatActivity(){



        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)

            val storeManager = StoreManager(this)


            if (storeManager.email != null && storeManager.email!!.isNotEmpty()) {
                setContentView(R.layout.detail)
                val extras = intent.extras
                val item = extras?.getSerializable("DATA") as JanjiList
                val dateFormat_yyyyMMddHHmmss = SimpleDateFormat(
                    "yyyy-MM-dd'T'HH:mm:ss.SSSSSSZ", Locale.ENGLISH
                )
                val date = dateFormat_yyyyMMddHHmmss.parse(item.created_at)
                val ended =  if (item.ended_at!= null) item.ended_at else item.created_at
                val date2 = dateFormat_yyyyMMddHHmmss.parse(ended)
                val calendar = Calendar.getInstance()
                val calendar2 = Calendar.getInstance()
                calendar.time = date
                calendar2.time = date2

                val kesimpulan = findViewById<TextView>(R.id.editTextTextMultiLine)
                tvharidetail.text = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.ENGLISH) + ", "+calendar.get(
                    Calendar.DAY_OF_MONTH) + " "+calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH) + " "+calendar.get(
                    Calendar.YEAR)
                tvjamdetail.text = calendar.get(Calendar.HOUR_OF_DAY).toString() + ":"+ calendar.get(
                    Calendar.MINUTE) + " - " + calendar2.get(Calendar.HOUR_OF_DAY).toString() + ":"+ calendar2.get(
                    Calendar.MINUTE)
                tvgurudetail.text = item.guru
                tvtopikdetail.text = "Topik : "+item.topik
                kesimpulan.text = item.kesimpulan


                arrowback.setOnClickListener{
                    this.finish()
                }

                val root = findViewById<View>(android.R.id.content).rootView

            } else {
                Log.d("FACHRY", storeManager.email+"")
                startActivity(Intent(applicationContext, LoginActivity::class.java ))
                this.finish()
            }
        }

}