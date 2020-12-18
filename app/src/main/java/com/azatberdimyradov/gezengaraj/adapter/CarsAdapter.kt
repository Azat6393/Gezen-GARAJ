package com.azatberdimyradov.gezengaraj.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.azatberdimyradov.gezengaraj.R
import com.azatberdimyradov.gezengaraj.model.CarsBrand
import kotlinx.android.synthetic.main.item_car_brands.view.*

class CarsAdapter(private val context: Context, private val listener: OnItemClickListener,var cars: CarsBrand):
    RecyclerView.Adapter<CarsAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarsAdapter.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_car_brands,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: CarsAdapter.ViewHolder, position: Int) {
        var car = cars.carModels[position]
        holder.car.text = car
        //holder.bind(car.carModels[position])
        holder.itemView.setOnClickListener {
            listener.onItemClick(car)
        }

    }

    override fun getItemCount(): Int {
        return if (cars.carModels.size > 0){
            cars.carModels.size
        }else {
            0
        }
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val car: TextView = itemView.car_brand_recycler_view_text_view

        fun bind(cars: CarsBrand){
            //car.text = cars
        }
    }

    interface OnItemClickListener{
        fun onItemClick(carModel: String)
    }


}