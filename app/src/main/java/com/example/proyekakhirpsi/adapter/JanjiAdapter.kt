package com.example.proyekakhirpsi.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.proyekakhirpsi.R
import com.example.proyekakhirpsi.models.JanjiList

class JanjiAdapter(private var janjiList : List<JanjiList>) : RecyclerView.Adapter<JanjiAdapter.ViewHolder>() {

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
        holder.hari.text = item.created_at
        holder.waktu.text = item.created_at
        holder.guru.text = item.guru
        holder.topik.text = item.topik
    }

    override fun getItemCount(): Int {
        return janjiList.size
    }

}