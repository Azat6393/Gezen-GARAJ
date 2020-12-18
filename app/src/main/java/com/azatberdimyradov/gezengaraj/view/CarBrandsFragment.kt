package com.azatberdimyradov.gezengaraj.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.azatberdimyradov.gezengaraj.R
import com.azatberdimyradov.gezengaraj.adapter.CarBrandsAdapter
import com.azatberdimyradov.gezengaraj.viewmodel.CarsViewModel
import kotlinx.android.synthetic.main.fragment_car_brands.*

class CarBrandsFragment: Fragment(R.layout.fragment_car_brands) {

    private lateinit var carBrandsAdapter: CarBrandsAdapter
    private val viewModel by lazy { ViewModelProvider(this).get(CarsViewModel::class.java) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val context = context
        carBrandsAdapter = CarBrandsAdapter(context!!)
        car_brands_recycler_view.adapter = carBrandsAdapter

        retry_button.setOnClickListener {
            retry_button.isVisible = false
            car_brands_recycler_view.isVisible = false
            progress_bar.isVisible = true
            viewModel.readCarsData()
            observeData()
        }

        observeData()
    }

    fun observeData(){
        viewModel.readCarsData().observe(viewLifecycleOwner, Observer {
            carBrandsAdapter.setListData(it)
            carBrandsAdapter.notifyDataSetChanged()
            println(it)
        })
        viewModel.carsLoading.observe(viewLifecycleOwner, Observer {
            if (it){
                car_brands_recycler_view.isVisible = false
                progress_bar.isVisible = true
            }else{
                car_brands_recycler_view.isVisible = true
                progress_bar.isVisible = false
            }
        })
        viewModel.carsError.observe(viewLifecycleOwner, Observer {
            if (it){
                retry_button.isVisible = true
                car_brands_recycler_view.isVisible = false
                progress_bar.isVisible = false
            }else {
                retry_button.isVisible = false
                car_brands_recycler_view.isVisible = true
                progress_bar.isVisible = true
            }
        })
    }

}