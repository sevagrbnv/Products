package ru.sevagrbnv.products.data.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import retrofit2.HttpException
import ru.sevagrbnv.products.domain.model.Product

class ProductListPagingSource(
    private val service: RetrofitService
): PagingSource<Int, Product>(){

    override fun getRefreshKey(state: PagingState<Int, Product>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition) ?: return null
        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Product> {

        val page = params.key ?: 1
        val pageSize = params.loadSize

        val response =
            service.getPagingProductList(
                page = page,
                limit = params.loadSize
            )

        return if (response.isSuccessful) {
            val articles = checkNotNull(response.body()).products.map { it.toProduct() }
            val nextKey = if (articles.size < pageSize) null else page + 1
            val prevKey = if (page == 1) null else page - 1
            LoadResult.Page(articles, prevKey, nextKey)
        } else {
            LoadResult.Error(HttpException(response))
        }
    }
}