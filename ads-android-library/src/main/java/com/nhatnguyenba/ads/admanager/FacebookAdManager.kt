package com.nhatnguyenba.ads.admanager

import android.app.Activity
import android.content.Context
import android.view.ViewGroup
import com.facebook.ads.NativeAdListener

class FacebookAdManager: AdManager {
    override fun initialize(context: Context) {
        TODO("Not yet implemented")
    }

    override fun loadBannerAd(context: Context, adUnitId: String, container: ViewGroup) {
        TODO("Not yet implemented")
    }

    override fun showBannerAd(container: ViewGroup) {
        TODO("Not yet implemented")
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
}