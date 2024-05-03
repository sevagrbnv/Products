package ru.sevagrbnv.products.domain.model.usecase

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.sevagrbnv.products.domain.model.Product
import ru.sevagrbnv.products.domain.model.Repository
import javax.inject.Inject

class GetPagingProductsUseCase @Inject constructor(
    private val repository: Repository
) {

    operator fun invoke(): Flow<PagingData<Product>> = repository.getProducts()
}