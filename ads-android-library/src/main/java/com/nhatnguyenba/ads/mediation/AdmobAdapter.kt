package com.nhatnguyenba.ads.mediation

import android.content.Context
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.LoadAdError

class AdmobAdapter : MediationAdapter {

    private var adView: AdView? = null

    override fun loadAd(context: Context, adUnitId: String, listener: MediationAdListener) {
        adView = AdView(context).apply {
            setAdSize(AdSize.BANNER)
            this.adUnitId = adUnitId
            adListener = object : AdListener() {
                override fun onAdLoaded() = listener.onAdLoaded(this@AdmobAdapter)
                override fun onAdFailedToLoad(error: LoadAdError) {
                    listener.onAdFailed("AdMob error: ${error.message}")
                }
            }
            loadAd(AdRequest.Builder().build())
        }
    }

    override fun destroy() {
        adView?.destroy()
    }
}