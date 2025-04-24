package com.nhatnguyenba.ads.mediation

import android.content.Context
import com.google.android.gms.ads.MobileAds
import com.nhatnguyenba.ads.admanager.AdmobManager
import com.nhatnguyenba.ads.admanager.FacebookAdManager

// AdMediation.kt
object AdMediation {
    fun initialize(context: Context) {
        val chain = MediationAdapterChain()
            .addAdapter(AdmobAdapter())
            .addAdapter(FacebookAdapter())

        MobileAds.initialize(context) {
            // Thiết lập mediation chain cho AdMob
//            MediationManager.setMediationChain(chain)
        }
    }
}