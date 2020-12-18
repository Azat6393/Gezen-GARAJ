package com.azatberdimyradov.gezengaraj.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CarsBrand (
    val carName: String,
    val carModels: List<String>
): Parcelable