package com.nhatnguyenba.quotelligent.presentation.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.nhatnguyenba.quotelligent.ads.QuotelligentAdManager
import com.nhatnguyenba.quotelligent.presentation.theme.QuoteAppTheme
import com.nhatnguyenba.quotelligent.presentation.theme.ThemeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        QuotelligentAdManager.initialize(this)

        setContent {
            val themeViewModel: ThemeViewModel = hiltViewModel()
            val isDarkMode = themeViewModel.isDarkMode.collectAsState().value
            QuoteAppTheme(
                darkTheme = isDarkMode
            ) {
                MainScreen()
            }
        }
    }
}