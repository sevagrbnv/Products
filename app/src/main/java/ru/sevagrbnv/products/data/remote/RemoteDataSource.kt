package ru.sevagrbnv.products.data.remote

import androidx.paging.Pager
import androidx.paging.PagingConfig
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val service: RetrofitService,
) {

    fun getProducts() = Pager(
        config = PagingConfig(pageSize = 20),
        pagingSourceFactory = { ProductListPagingSource(service) }
    ).flow
}