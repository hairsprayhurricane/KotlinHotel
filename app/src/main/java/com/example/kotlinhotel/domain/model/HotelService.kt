package com.example.kotlinhotel.domain.model

import android.os.Parcel
import android.os.Parcelable

data class HotelService(
    val id: String,
    val title: String,
    val description: String,
    val price: Double,
    val category: ServiceCategory,
    val iconRes: Int = 0
) : Parcelable {

    constructor(parcel: Parcel) : this(
        id = parcel.readString() ?: "",
        title = parcel.readString() ?: "",
        description = parcel.readString() ?: "",
        price = parcel.readDouble(),
        category = ServiceCategory.entries[parcel.readInt()],
        iconRes = parcel.readInt()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(id)
        dest.writeString(title)
        dest.writeString(description)
        dest.writeDouble(price)
        dest.writeInt(category.ordinal)
        dest.writeInt(iconRes)
    }

    companion object CREATOR : Parcelable.Creator<HotelService> {
        override fun createFromParcel(parcel: Parcel) = HotelService(parcel)
        override fun newArray(size: Int): Array<HotelService?> = arrayOfNulls(size)
    }
}

enum class ServiceCategory {
    SPA, FOOD, TRANSFER, EXCURSION, LAUNDRY, CLEANING
}
