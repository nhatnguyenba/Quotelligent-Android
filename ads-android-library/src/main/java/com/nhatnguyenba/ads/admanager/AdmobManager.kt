package com.nhatnguyenba.ads.admanager

import android.app.Activity
import android.content.Context
import android.view.ViewGroup
import com.facebook.ads.AudienceNetworkAds
import com.facebook.ads.NativeAdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.rewarded.RewardedAd
import com.nhatnguyenba.ads.models.AdConfig
import javax.inject.Inject

// AdmobManager.kt
class AdmobManager @Inject constructor(
    private val adConfig: AdConfig
) : AdManager {

    private var interstitialAd: InterstitialAd? = null
    private var rewardedAd: RewardedAd? = null

    override fun initialize(context: Context) {
        MobileAds.initialize(context) {
            // Khởi tạo Mediation
            if (adConfig.enableFacebookMediation) {
                AudienceNetworkAds.initialize(context) // Facebook SDK
            }
        }
    }

    override fun loadBannerAd(context: Context, adUnitId: String, container: ViewGroup) {
        AdView(context).apply {
            this.setAdSize(AdSize.BANNER)
            this.adUnitId = getActualAdUnit(adUnitId)
            this.loadAd(AdRequest.Builder().build())
            container.addView(this)
        }
    }

    override fun showBannerAd(container: ViewGroup) {
        
    }

    override fun loadInterstitialAd(context: Context, adUnitId: String) {
        TODO("Not yet implemented")
    }

    override fun showInterstitialAd(activity: Activity) {
        TODO("Not yet implemented")
    }

    override fun loadNativeAd(context: Context, adUnitId: String, adListener: NativeAdListener) {
        TODO("Not yet implemented")
    }

    override fun loadRewardedAd(adUnitId: String) {
        TODO("Not yet implemented")
    }

    override fun showRewardedAd(activity: Activity, onReward: (reward: Int) -> Unit) {
        TODO("Not yet implemented")
    }

    override fun enableMediation(enable: Boolean) {
        TODO("Not yet implemented")
    }

    private fun getActualAdUnit(adUnitId: String): String {
        return if (adConfig.useTestAds) {
            when (adUnitId) {
                "banner" -> "ca-app-pub-3940256099942544/9214589741" // Test ID
                else -> adUnitId
            }
        } else {
            adUnitId
        }
    }
}