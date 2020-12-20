package com.example.projekt.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.projekt.R
import com.example.projekt.models.Restaurant


class RestaurantAdapter(
    private val list: List<Restaurant>,
    private val listener: OnItemClickListener
) :
    RecyclerView.Adapter<RestaurantAdapter.ExampleViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExampleViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.costum_row,
            parent, false)
        return ExampleViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: ExampleViewHolder, position: Int) {
        val currentItem = list[position]
        holder.imageView.setImageResource(R.drawable.food)
        holder.textView1.text = currentItem.name
        holder.textView2.text = currentItem.address
        holder.textView3.text = currentItem.price.toString()
    }
    override fun getItemCount() = list.size
    inner class ExampleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val imageView: ImageView = itemView.findViewById(R.id.img_res)
        val textView1: TextView = itemView.findViewById(R.id.name_res)
        val textView2: TextView = itemView.findViewById(R.id.address_res)
        val textView3: TextView = itemView.findViewById(R.id.price)
        init {
            itemView.setOnClickListener(this)
        }
        override fun onClick(v: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(position)
            }
        }
    }
    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }
}