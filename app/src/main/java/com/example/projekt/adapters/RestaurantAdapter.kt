package com.example.projekt.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.projekt.R
import com.example.projekt.models.Restaurant
import kotlinx.android.synthetic.main.costum_row.view.*


class RestaurantAdapter(private val list : List<Restaurant>, private val listener : OnItemClickListener, private val context : Context) :
    RecyclerView.Adapter<RestaurantAdapter.ExampleViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExampleViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.costum_row,
            parent, false)
        return ExampleViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ExampleViewHolder, position: Int) {
        val currentItem = list[position]
        //holder.imageView.setImageResource(R.drawable.food)
        Glide.with(context)
            .load(currentItem.image_url)
            .into(holder.imageView)
        holder.textView1.text = currentItem.name
        holder.textView2.text = currentItem.address
        holder.textView3.text = currentItem.price.toString()

        var image1 : String = "https://www.szekelyfoldiinfo.ro/Menu/Szallashelyek/Kepek/marosvasarhely-szallas-lyra_panzio-1.jpg"
        var image2 : String = "http://www.seunkutiandegypt80.com/wp-content/uploads/2020/03/5-Tips-for-Improving-Restaurant-Ambiance.jpg"
        var image3 : String = "https://www.erdelyiutazas.hu/images/photo/36/1-365-44dbe68992b.jpg"

        /*val rand = (0..3).random()
        when (rand % 3) {
            0 -> {
                Glide.with(context)
                    .load(image1)
                    .into(holder.imageView)
            }
            1 -> {
                Glide.with(context)
                    .load(image2)
                    .into(holder.imageView)
            }
            else -> {
                Glide.with(context)
                    .load(image3)
                    .into(holder.imageView)
            }
        }*/

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