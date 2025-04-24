package com.nhatnguyenba.ads.mediation

import android.content.Context
import com.facebook.ads.Ad
import com.facebook.ads.AdError
import com.facebook.ads.AdSize
import com.facebook.ads.AdView

class FacebookAdapter : MediationAdapter {

    private var adView: com.facebook.ads.AdView? = null

    override fun loadAd(context: Context, adUnitId: String, listener: MediationAdListener) {
        adView = com.facebook.ads.AdView(context, adUnitId, AdSize.BANNER_HEIGHT_50).apply {
            loadAd(object : com.facebook.ads.AdListener, AdView.AdViewLoadConfig {
                override fun onAdLoaded(ad: Ad) = listener.onAdLoaded(this@FacebookAdapter)
                override fun onAdClicked(p0: Ad?) {
                    TODO("Not yet implemented")
                }

                override fun onLoggingImpression(p0: Ad?) {
                    TODO("Not yet implemented")
                }

                override fun onError(ad: Ad, error: AdError) {
                    listener.onAdFailed("Facebook error: ${error.errorMessage}")
                }
            })
        }
    }

    override fun destroy() {
        adView?.destroy()
    }
}