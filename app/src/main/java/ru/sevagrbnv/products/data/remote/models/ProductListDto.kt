package ru.sevagrbnv.products.data.remote.models

data class ProductListDto(
    val limit: Int,
    val products: List<ProductDto>,
    val skip: Int,
    val total: Int
)