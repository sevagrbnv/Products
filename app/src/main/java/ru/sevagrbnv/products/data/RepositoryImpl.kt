package ru.sevagrbnv.products.data

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.sevagrbnv.products.data.remote.RemoteDataSource
import ru.sevagrbnv.products.domain.model.Product
import ru.sevagrbnv.products.domain.model.Repository
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) : Repository {

    override fun getProducts(): Flow<PagingData<Product>> = remoteDataSource.getProducts()
}