package com.example.androidui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TabLayoutViewPager2Adapter(var textItems: ArrayList<String>): RecyclerView.Adapter<TabLayoutViewPager2Adapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.viewpager_frame, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return textItems.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(textItems[position])
    }

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        fun bind(text: String) {
            val textView = view.findViewById<TextView>(R.id.tvText)
            textView.text = text
        }
    }
}