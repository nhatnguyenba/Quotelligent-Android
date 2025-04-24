package com.nhatnguyenba.quotelligent.ads

import android.content.Context
import com.nhatnguyenba.ads.admanager.AdManager
import com.nhatnguyenba.ads.admanager.AdmobManager
import com.nhatnguyenba.ads.models.AdConfig
import javax.inject.Inject

object QuotelligentAdManager{
    val adManager: AdManager by lazy {
        AdmobManager(AdConfig(
            useTestAds = true,
            enableFacebookMediation = false
        ))
    }

    fun initialize(context: Context) {
        adManager.initialize(context)
    }
}