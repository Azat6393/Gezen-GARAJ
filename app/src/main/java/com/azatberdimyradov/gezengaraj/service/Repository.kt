package com.azatberdimyradov.gezengaraj.service

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.azatberdimyradov.gezengaraj.model.CarsBrand
import com.azatberdimyradov.gezengaraj.model.PlayerID
import com.google.firebase.firestore.FirebaseFirestore

class Repository {


    fun getCarsName(): LiveData<MutableList<CarsBrand>> {
        val mutableData = MutableLiveData<MutableList<CarsBrand>>()

        FirebaseFirestore.getInstance().collection("Cars")
            .get()
            .addOnSuccessListener { result ->
                val liveData = mutableListOf<CarsBrand>()
                if (result != null){
                    for (document in result) {
                        val carName = document.id
                        var carModels = document.get("models") as ArrayList<String>
                        val carsBrand = CarsBrand(carName,carModels)
                        liveData.add(carsBrand)
                    }
                    mutableData.value = liveData
                }else{
                    println("null")
                }

            }.addOnFailureListener {
                println(it.localizedMessage.toString())
            }
        return mutableData
    }

    fun getPlayerID(): LiveData<MutableList<PlayerID>>{
        val mutableData = MutableLiveData<MutableList<PlayerID>>()

        FirebaseFirestore.getInstance().collection("PlayerID")
            .get()
            .addOnSuccessListener { result ->
                val liveData = mutableListOf<PlayerID>()
                if (result != null){
                    for (docunmet in result){
                        val id = docunmet.getString("id")
                        val playerID = PlayerID(id!!)
                        liveData.add(playerID)
                    }
                    mutableData.value = liveData
                }
            }
        return mutableData
    }

}