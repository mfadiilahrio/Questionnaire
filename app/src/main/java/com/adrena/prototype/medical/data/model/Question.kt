package com.adrena.prototype.medical.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Question(
    val id: Int,
    val question: String,
    val category: Category? = null,
    val options: List<Option>,
    var number: String = "0",
    var answer: String? = null
): Parcelable {

    @Parcelize
    data class Option(
        val answer: String,
        val point: Int,
        var checked: Boolean = false
    ): Parcelable

}