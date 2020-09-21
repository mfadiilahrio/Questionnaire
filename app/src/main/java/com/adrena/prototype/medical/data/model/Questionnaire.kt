package com.adrena.prototype.medical.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Questionnaire(
    val name: String,
    val questions: List<Question>
): Parcelable