package com.nhatnguyenba.quotelligent.presentation.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.nhatnguyenba.quotelligent.presentation.navigation.BottomNavigationBar
import com.nhatnguyenba.quotelligent.presentation.navigation.Screen
import com.nhatnguyenba.quotelligent.presentation.screen.collection.CollectionScreen
import com.nhatnguyenba.quotelligent.presentation.screen.home.HomeScreen
import com.nhatnguyenba.quotelligent.presentation.screen.profile.ProfileScreen
import com.nhatnguyenba.quotelligent.presentation.screen.search.SearchScreen

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val screens = listOf(
        Screen.Home,
        Screen.Search,
        Screen.Collection,
        Screen.Profile
    )

    Scaffold(
        bottomBar = { BottomNavigationBar(navController, screens) }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = androidx.compose.ui.Modifier.padding(padding)
        ) {
            composable(Screen.Home.route) { HomeScreen() }
            composable(Screen.Search.route) { SearchScreen() }
            composable(Screen.Collection.route) { CollectionScreen() }
            composable(Screen.Profile.route) { ProfileScreen() }
        }
    }
}