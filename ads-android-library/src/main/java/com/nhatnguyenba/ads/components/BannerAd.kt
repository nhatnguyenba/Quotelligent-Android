package com.nhatnguyenba.ads.components

import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.nhatnguyenba.ads.admanager.AdManager

// BannerAd.kt
@Composable
fun BannerAd(
    adManager: AdManager,
    adUnitId: String,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var adContainer by remember { mutableStateOf<ViewGroup?>(null) }

    AndroidView(
        factory = { ctx ->
            FrameLayout(ctx).apply {
                adContainer = this
            }
        },
        modifier = modifier
    )

    LaunchedEffect(adUnitId) {
        adContainer?.let { adManager.loadBannerAd(context, adUnitId, it) }
    }
}