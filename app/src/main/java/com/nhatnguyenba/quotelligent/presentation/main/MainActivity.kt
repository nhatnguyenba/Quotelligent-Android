package com.nhatnguyenba.quotelligent.presentation.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.nhatnguyenba.quotelligent.ads.QuotelligentAdManager
import com.nhatnguyenba.quotelligent.presentation.theme.QuoteAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        QuotelligentAdManager.initialize(this)

        setContent {
            QuoteAppTheme {
                MainScreen()
            }
        }
    }
}