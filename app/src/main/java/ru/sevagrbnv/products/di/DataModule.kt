package ru.sevagrbnv.products.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.sevagrbnv.products.data.RepositoryImpl
import ru.sevagrbnv.products.data.remote.RemoteDataSource
import ru.sevagrbnv.products.data.remote.RetrofitService
import ru.sevagrbnv.products.domain.model.Repository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    @Singleton
    @Provides
    fun providesHttpLoggingInterceptor() =
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BASIC
        }

    @Singleton
    @Provides
    fun providesOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor) =
        OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()

    @Singleton
    @Provides
    fun providesRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl("https://dummyjson.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()

    @Singleton
    @Provides
    fun providesRetrofitService(retrofit: Retrofit): RetrofitService =
        retrofit.create(RetrofitService::class.java)

    @Provides
    @Singleton
    fun provideRemoteDataSource(
        service: RetrofitService,
    ): RemoteDataSource {
        return RemoteDataSource(service)
    }


    @Provides
    @Singleton
    fun provideRepository(
        remoteDataSource: RemoteDataSource,
    ): Repository {
        return RepositoryImpl(
            remoteDataSource = remoteDataSource
        )
    }
}