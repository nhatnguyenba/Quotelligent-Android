package com.nhatnguyenba.quotelligent.config

import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteConfigManager @Inject constructor() {
    private val remoteConfig = FirebaseRemoteConfig.getInstance()

    init {
        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = 3600 // 1 giờ
        }
        remoteConfig.setConfigSettingsAsync(configSettings)
        remoteConfig.setDefaultsAsync(
            mapOf(
                "pexels_api_key" to "YOUR_DEFAULT_PEXELS_API_KEY",
                "favqs_api_key" to "YOUR_DEFAULT_FAVQS_API_KEY",
                "pexels_base_url" to "https://api.pexels.com/v1/",
                "favqs_base_url" to "https://favqs.com/api/"
            )
        )
    }

    fun fetchConfig() {
        remoteConfig.fetchAndActivate()
    }

    fun getPexelsApiKey() = remoteConfig.getString("pexels_api_key")
    fun getFavqsApiKey() = remoteConfig.getString("favqs_api_key")
    fun getPexelsBaseUrl() = remoteConfig.getString("pexels_base_url")
    fun getFavqsBaseUrl() = remoteConfig.getString("favqs_base_url")
}