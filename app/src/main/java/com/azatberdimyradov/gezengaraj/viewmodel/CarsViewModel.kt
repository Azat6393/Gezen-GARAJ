package com.azatberdimyradov.gezengaraj.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.azatberdimyradov.gezengaraj.model.CarsBrand
import com.azatberdimyradov.gezengaraj.model.PlayerID
import com.azatberdimyradov.gezengaraj.service.Repository

class CarsViewModel: ViewModel() {

    private val repository = Repository()
    val carsLoading = MutableLiveData<Boolean>()
    val carsError = MutableLiveData<Boolean>()

    fun readCarsData(): LiveData<MutableList<CarsBrand>>{
        carsLoading.value = true
        val mutableData = MutableLiveData<MutableList<CarsBrand>>()
        repository.getCarsName().observeForever { carsList->
            mutableData.value = carsList
            carsError.value = carsList.isEmpty()
            carsLoading.value = false
        }
        return mutableData
    }
    fun readPlayerID(): LiveData<MutableList<PlayerID>>{
        val mutableData = MutableLiveData<MutableList<PlayerID>>()
        repository.getPlayerID().observeForever { idList ->
            mutableData.value = idList
        }
        return mutableData
    }

}