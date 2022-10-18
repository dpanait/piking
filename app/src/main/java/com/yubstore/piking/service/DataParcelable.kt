package com.yubstore.piking.service

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ProductSearch(
    val idClient: String,
    val ordersId: String?
): Parcelable