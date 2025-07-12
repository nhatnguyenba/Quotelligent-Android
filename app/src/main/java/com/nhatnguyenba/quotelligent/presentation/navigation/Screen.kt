package com.nhatnguyenba.quotelligent.presentation.navigation

import com.nhatnguyenba.quotelligent.R

sealed class Screen(
    val route: String,
    val title: String,
    val iconRes: Int?
) {
    object Home : Screen("home", "Home", R.drawable.ic_home)
    object Search : Screen("search", "Search", R.drawable.ic_search)
    object Collection : Screen("collection", "Collection", R.drawable.ic_collection)
    object Profile : Screen("profile", "Profile", R.drawable.ic_profile)

    object QuoteDetail : Screen(route = "quote_detail}", "Quote Detail", null) {
        const val ARG_QUOTE_ID = "quoteId"
    }

    object AuthorDetail : Screen("author_detail}", "Author Detail", null) {
        const val ARG_AUTHOR_ID = "authorId"
    }

    object CategoryDetail : Screen("category_detail}", "Category Detail", null) {
        const val ARG_CATEGORY_ID = "categoryId"
    }
}