package com.gabreucast.projetotodolist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyItemAdapter(val itemList: List<MyItens>) : RecyclerView.Adapter<MyItemAdapter.MyItemVH>() {


    inner class MyItemVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTV = itemView.findViewById<TextView>(R.id.titleTV)
        val taskTV = itemView.findViewById<TextView>(R.id.taskTV)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyItemVH {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.fragment_task_list, parent, false)
        return MyItemVH(itemView)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: MyItemVH, position: Int) {
        val currentItem = itemList[position]
        holder.titleTV.text = currentItem.title
        holder.taskTV.text = currentItem.task

    }

}