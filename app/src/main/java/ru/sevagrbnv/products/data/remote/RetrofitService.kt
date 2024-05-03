package ru.sevagrbnv.products.data.remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import ru.sevagrbnv.products.data.remote.models.ProductListDto

interface RetrofitService {

    @GET("products")
    suspend fun getPagingProductList(
        @Query("skip") page: Int,
        @Query("limit") limit: Int
    ) : Response<ProductListDto>
}