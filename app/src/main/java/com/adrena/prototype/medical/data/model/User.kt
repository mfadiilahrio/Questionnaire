package com.adrena.prototype.medical.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    val name: String?,
    val gender: String?,
    val age: Int?,
    val birthPlace: String?,
    val dob: String?,
    val address: String?,
    val weight: Int?,
    val height: Int?,
    val neckCircumference: Int?
) : Parcelable