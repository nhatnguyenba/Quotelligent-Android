package com.nhatnguyenba.ads.admanager

import android.app.Activity
import android.content.Context
import android.view.ViewGroup
import com.facebook.ads.NativeAdListener

interface AdManager {
    fun initialize(context: Context)

    // Các phương thức ads
    fun loadBannerAd(context: Context, adUnitId: String, container: ViewGroup)
    fun showBannerAd(container: ViewGroup)

    fun loadInterstitialAd(context: Context, adUnitId: String)
    fun showInterstitialAd(activity: Activity)

    fun loadNativeAd(context: Context, adUnitId: String, adListener: NativeAdListener)

    // Rewarded & Mediation
    fun loadRewardedAd(adUnitId: String)
    fun showRewardedAd(activity: Activity, onReward: (reward: Int) -> Unit)

    fun enableMediation(enable: Boolean)
}