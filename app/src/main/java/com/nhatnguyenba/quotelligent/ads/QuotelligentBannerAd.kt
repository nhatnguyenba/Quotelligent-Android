package com.nhatnguyenba.quotelligent.ads

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.nhatnguyenba.ads.components.BannerAd

@Composable
fun QuotelligentBannerAd(
    modifier: Modifier = Modifier.fillMaxSize(),
) {
    BannerAd(
        adManager = QuotelligentAdManager.adManager,
        modifier = modifier,
        adUnitId = "banner"
    )
}