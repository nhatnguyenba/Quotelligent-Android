package com.nhatnguyenba.ads.models

import com.facebook.ads.BuildConfig

class AdConfig(
    val useTestAds: Boolean = BuildConfig.DEBUG,
    val enableFacebookMediation: Boolean = true
) {

}