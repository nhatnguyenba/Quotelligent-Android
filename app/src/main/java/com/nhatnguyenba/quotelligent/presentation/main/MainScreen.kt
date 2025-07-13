package com.nhatnguyenba.quotelligent.presentation.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.nhatnguyenba.quotelligent.presentation.navigation.BottomNavigationBar
import com.nhatnguyenba.quotelligent.presentation.navigation.Screen
import com.nhatnguyenba.quotelligent.presentation.screen.collection.CollectionScreen
import com.nhatnguyenba.quotelligent.presentation.screen.detail.AuthorDetailScreen
import com.nhatnguyenba.quotelligent.presentation.screen.detail.CategoryDetailScreen
import com.nhatnguyenba.quotelligent.presentation.screen.detail.QuoteDetailScreen
import com.nhatnguyenba.quotelligent.presentation.screen.home.HomeScreen
import com.nhatnguyenba.quotelligent.presentation.screen.home.HomeViewModel
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
            modifier = Modifier.padding(padding)
        ) {
            composable(Screen.Home.route) {
                val viewModel: HomeViewModel = hiltViewModel()
                HomeScreen(quoteList = viewModel.cachedPages)
            }
            composable(Screen.Search.route) { SearchScreen(navController = navController) }
            composable(Screen.Collection.route) { CollectionScreen() }
            composable(Screen.Profile.route) { ProfileScreen() }

            // Quote Detail
            composable(
                route = "${Screen.QuoteDetail.route}/{${Screen.QuoteDetail.ARG_QUOTE_ID}}",
                arguments = listOf(navArgument(Screen.QuoteDetail.ARG_QUOTE_ID) {
                    type = NavType.StringType
                })
            ) { backStackEntry ->
                val quoteId =
                    backStackEntry.arguments?.getString(Screen.QuoteDetail.ARG_QUOTE_ID) ?: ""
                QuoteDetailScreen(
                    quoteId = quoteId,
                    onBack = { navController.popBackStack() }
                )
            }

            // Author Detail
            composable(
                route = "${Screen.AuthorDetail.route}/{${Screen.AuthorDetail.ARG_AUTHOR_ID}}",
                arguments = listOf(navArgument(Screen.AuthorDetail.ARG_AUTHOR_ID) {
                    type = NavType.StringType
                })
            ) { backStackEntry ->
                val author =
                    backStackEntry.arguments?.getString(Screen.AuthorDetail.ARG_AUTHOR_ID) ?: ""
                AuthorDetailScreen(
                    authorName = author,
                    onBack = { navController.popBackStack() },
                    navController = navController
                )
            }

            // Category Detail
            composable(
                route = "${Screen.CategoryDetail.route}/{${Screen.CategoryDetail.ARG_CATEGORY_ID}}",
                arguments = listOf(navArgument(Screen.CategoryDetail.ARG_CATEGORY_ID) {
                    type = NavType.StringType
                })
            ) { backStackEntry ->
                val category =
                    backStackEntry.arguments?.getString(Screen.CategoryDetail.ARG_CATEGORY_ID) ?: ""
                CategoryDetailScreen(
                    categoryName = category,
                    onBack = { navController.popBackStack() },
                    navController = navController
                )
            }
        }

//        QuotelligentBannerAd(
//            modifier = Modifier.fillMaxSize()
//        )
    }
}