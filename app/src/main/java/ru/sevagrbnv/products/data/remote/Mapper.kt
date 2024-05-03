package ru.sevagrbnv.products.data.remote

import ru.sevagrbnv.products.data.remote.models.ProductDto
import ru.sevagrbnv.products.domain.model.Product

fun ProductDto.toProduct() = Product(
    id = id,
    title = title,
    description = description,
    thumbnail = thumbnail,
    brand = brand,
    rating = rating,
    price = price,
    discount = discountPercentage,
    category = category,
    images = images

)