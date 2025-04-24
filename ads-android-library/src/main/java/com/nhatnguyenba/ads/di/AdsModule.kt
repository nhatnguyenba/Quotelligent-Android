package com.nhatnguyenba.ads.di

import com.nhatnguyenba.ads.admanager.AdManager
import com.nhatnguyenba.ads.admanager.AdmobManager
import com.nhatnguyenba.ads.models.AdConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AdsModule {

    @Provides
    @Singleton
    fun provideAdConfig(): AdConfig {
        return AdConfig(
            useTestAds = true,
            enableFacebookMediation = false
        )
    }

    @Provides
    @Singleton
    fun provideAdManager(adConfig: AdConfig): AdManager {
        return AdmobManager(adConfig)
    }
}