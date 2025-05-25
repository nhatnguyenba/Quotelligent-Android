package com.nhatnguyenba.quotelligent.di

import android.util.Log
import com.nhatnguyenba.quotelligent.config.RemoteConfigManager
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

    @Provides
    @Singleton
    fun provideQuoteApi(
        remoteConfigManager: RemoteConfigManager
    ): FavQsQuoteApiService {
        val baseUrl = remoteConfigManager.getFavqsBaseUrl()
        val apiKey = remoteConfigManager.getFavqsApiKey()

        Log.d("NetworkModule", "baseUrl: $baseUrl, apiKey: $apiKey")

        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(HttpLoggingInterceptor().apply {
                        level = HttpLoggingInterceptor.Level.BODY
                    })
                    .addInterceptor { chain ->
                        val request = chain.request().newBuilder()
                            .addHeader("Authorization", apiKey)
                            .build()
                        chain.proceed(request)
                    }
                    .build()
            )
            .build()
            .create(FavQsQuoteApiService::class.java)
    }

    @Provides
    @Singleton
    fun providePexelsApi(
        remoteConfigManager: RemoteConfigManager
    ): PexelsApiService {
        val baseUrl = remoteConfigManager.getPexelsBaseUrl()
        val apiKey = remoteConfigManager.getPexelsApiKey()

        Log.d("NetworkModule2", "baseUrl: $baseUrl, apiKey: $apiKey")

        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(HttpLoggingInterceptor().apply {
                        level = HttpLoggingInterceptor.Level.BODY
                    })
                    .addInterceptor { chain ->
                        val request = chain.request().newBuilder()
                            .addHeader("Authorization", apiKey)
                            .build()
                        chain.proceed(request)
                    }
                    .build()
            )
            .build()
            .create(PexelsApiService::class.java)
    }
}