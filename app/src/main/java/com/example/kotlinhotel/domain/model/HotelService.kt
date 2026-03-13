package com.example.kotlinhotel.domain.model

import android.os.Parcel
import android.os.Parcelable

enum class ServiceCategory {
    SPA, FOOD, TRANSFER, EXCURSION, CLEANING, LAUNDRY
}

data class HotelService(
    val id: Int,
    val title: String,
    val description: String,
    val price: Double,
    val category: ServiceCategory,
    val iconEmoji: String = ""
) : Parcelable {

    constructor(parcel: Parcel) : this(
        id = parcel.readInt(),
        title = parcel.readString() ?: "",
        description = parcel.readString() ?: "",
        price = parcel.readDouble(),
        category = ServiceCategory.valueOf(parcel.readString() ?: ServiceCategory.SPA.name),
        iconEmoji = parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(title)
        parcel.writeString(description)
        parcel.writeDouble(price)
        parcel.writeString(category.name)
        parcel.writeString(iconEmoji)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<HotelService> {
        override fun createFromParcel(parcel: Parcel) = HotelService(parcel)
        override fun newArray(size: Int) = arrayOfNulls<HotelService>(size)
    }
}
