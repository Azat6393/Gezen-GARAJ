package com.azatberdimyradov.gezengaraj.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.azatberdimyradov.gezengaraj.R
import com.azatberdimyradov.gezengaraj.model.CarsBrand
import com.azatberdimyradov.gezengaraj.view.CarBrandsFragmentDirections
import kotlinx.android.synthetic.main.item_car_brands.view.*

class CarBrandsAdapter(private val context: Context):
    RecyclerView.Adapter<CarBrandsAdapter.ViewHolder>() {

    private var carsList = mutableListOf<CarsBrand>()

    fun setListData(data: MutableList<CarsBrand>){
        carsList = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarBrandsAdapter.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_car_brands,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: CarBrandsAdapter.ViewHolder, position: Int) {

        var car = carsList[position]
        holder.bind(car)
        holder.itemView.setOnClickListener {
            val action = CarBrandsFragmentDirections.actionCarBrandsFragmentToCarModelsFragment(carsList[position])
            Navigation.findNavController(holder.itemView).navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return if (carsList.size > 0){
            carsList.size
        }else{
            0
        }
    }


    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        val car: TextView = itemView.car_brand_recycler_view_text_view

        fun bind(cars: CarsBrand){
            car.text = cars.carName
        }
    }
}