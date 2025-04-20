package com.nhatnguyenba.quotelligent.di

import com.nhatnguyenba.quotelligent.data.remote.api.FavQsQuoteApiService
import com.nhatnguyenba.quotelligent.data.remote.api.PexelsApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val PEXELS_API_KEY = "ma516sPtoPIyAjWXUeDCAPQQsG4XBNFYQQfWdntZnmEFRwgxlstwA2SS"
    private const val FAVQS_API_KEY = "319fa0de99dbf6677db87a7614a400e0"

    @Provides
    @Singleton
    fun provideQuoteApi(): FavQsQuoteApiService = Retrofit.Builder()
        .baseUrl("https://favqs.com/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(
            OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
                .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("Authorization", FAVQS_API_KEY)
                    .build()
                chain.proceed(request)
            }
                .build()
        )
        .build()
        .create(FavQsQuoteApiService::class.java)

    @Provides
    @Singleton
    fun providePexelsApi(): PexelsApiService = Retrofit.Builder()
        .baseUrl("https://api.pexels.com/v1/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(
            OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
                .addInterceptor { chain ->
                    val request = chain.request().newBuilder()
                        .addHeader("Authorization", PEXELS_API_KEY)
                        .build()
                    chain.proceed(request)
                }
                .build()
        )
        .build()
        .create(PexelsApiService::class.java)
}