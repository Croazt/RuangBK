package com.example.proyekakhirpsi.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.proyekakhirpsi.DetailRiwayatActivity
import com.example.proyekakhirpsi.R
import com.example.proyekakhirpsi.models.JanjiList
import java.text.SimpleDateFormat
import java.util.*

class JanjiAdapter(private var janjiList : List<JanjiList>, context: Context) : RecyclerView.Adapter<JanjiAdapter.ViewHolder>() {
    val mContext = context
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        var hari : TextView = view.findViewById(R.id.hari)
        var waktu: TextView = view.findViewById(R.id.waktu)
        var guru: TextView = view.findViewById(R.id.guru)
        var topik: TextView = view.findViewById(R.id.topik)
        var btndetail: Button = view.findViewById(R.id.btndetail)

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_janji, parent, false)

        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = janjiList[position]

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
        Log.d("DATE", "onBindViewHolder: "+calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH))
        holder.hari.text = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.ENGLISH) + ", "+calendar.get(Calendar.DAY_OF_MONTH) + " "+calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH) + " "+calendar.get(Calendar.YEAR)
        holder.waktu.text = calendar.get(Calendar.HOUR_OF_DAY).toString() + ":"+ calendar.get(Calendar.MINUTE) + " - " + calendar2.get(Calendar.HOUR_OF_DAY).toString() + ":"+ calendar2.get(Calendar.MINUTE)
        holder.guru.text = item.guru
        holder.topik.text = item.topik

        holder.btndetail.setOnClickListener{
            Log.d("TAG", "onBindViewHolder: ${janjiList[position]}")
            val i = Intent(mContext, DetailRiwayatActivity::class.java)
            i.putExtra("DATA", janjiList[position])
            mContext.startActivity(i)
        }
    }

    override fun getItemCount(): Int {
        return janjiList.size
    }

}