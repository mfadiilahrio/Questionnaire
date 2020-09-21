package com.adrena.prototype.medical.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Category(
    val name: String,
    val questionnaire: String
): Parcelable