package ru.sevagrbnv.products.domain.model

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow

interface Repository {

    fun getProducts(): Flow<PagingData<Product>>
}