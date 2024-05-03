package ru.sevagrbnv.products.data.remote.models

data class ProductDto(
    val id: Long,
    val title: String,
    val description: String,
    val thumbnail: String,

    val brand: String,
    val category: String,
    val discountPercentage: Double,
    val images: List<String>,
    val price: Int,
    val rating: Double,
    val stock: Int
)