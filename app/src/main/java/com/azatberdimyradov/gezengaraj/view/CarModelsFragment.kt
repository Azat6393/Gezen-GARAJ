package com.azatberdimyradov.gezengaraj.view

import android.app.Dialog
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.azatberdimyradov.gezengaraj.R
import com.azatberdimyradov.gezengaraj.adapter.CarsAdapter
import kotlinx.android.synthetic.main.choose_year_dialog.*
import kotlinx.android.synthetic.main.fragment_car_models.*

class CarModelsFragment: Fragment(R.layout.fragment_car_models), CarsAdapter.OnItemClickListener {

    private lateinit var carBrandsAdapter: CarsAdapter
    private val args by navArgs<CarModelsFragmentArgs>()
    private lateinit var dialog: Dialog

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val context = context
        dialog = Dialog(context!!)

        carBrandsAdapter = CarsAdapter(context!!,this,args.car)
        car_models_recyclerView.adapter = carBrandsAdapter
    }

    override fun onItemClick(carModel: String) {
        dialog.setContentView(R.layout.choose_year_dialog)
        dialog.number_picker.minValue = 1950
        dialog.number_picker.maxValue = 2021
        dialog.choose_year_button.setOnClickListener {
            val action = CarModelsFragmentDirections.actionCarModelsFragmentToDashboardFragment(args.car.carName
                ,carModel
                ,dialog.number_picker.value.toString())
            findNavController().navigate(action)
            dialog.dismiss()
        }
        dialog.show()
    }
}