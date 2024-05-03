package ru.sevagrbnv.products.domain.model

import android.os.Parcel
import android.os.Parcelable

data class Product(
    val id: Long,
    val title: String,
    val description: String,
    val thumbnail: String,
    val brand: String,
    val rating: Double,
    val price: Int,
    val discount: Double,
    val category: String,
    val images: List<String>
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readDouble(),
        parcel.readInt(),
        parcel.readDouble(),
        parcel.readString() ?: "",
        parcel.createStringArrayList() ?: listOf()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeString(title)
        parcel.writeString(description)
        parcel.writeString(thumbnail)
        parcel.writeString(brand)
        parcel.writeDouble(rating)
        parcel.writeInt(price)
        parcel.writeDouble(discount)
        parcel.writeString(category)
        parcel.writeStringList(images)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<Product> {

        override fun createFromParcel(parcel: Parcel): Product = Product(parcel)

        override fun newArray(size: Int): Array<Product?> = arrayOfNulls(size)
    }
}