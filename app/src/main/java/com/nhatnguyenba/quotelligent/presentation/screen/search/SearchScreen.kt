package com.nhatnguyenba.quotelligent.presentation.screen.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.nhatnguyenba.quotelligent.presentation.component.FilterChips
import com.nhatnguyenba.quotelligent.presentation.component.SearchBar
import com.nhatnguyenba.quotelligent.presentation.component.SearchResults

@Composable
fun SearchScreen(viewModel: SearchViewModel = hiltViewModel()) {
    val state by viewModel.state

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Search Bar
        SearchBar(state.query, onQueryChange = { viewModel.onQueryChange(it) })

        Spacer(modifier = Modifier.height(16.dp))

        // Filter Chips
        FilterChips(
            selectedFilter = state.selectedFilter,
            onFilterSelected = { viewModel.onFilterSelected(it) }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Search Results
        SearchResults(
            results = state.results,
            type = state.selectedFilter,
            isLoading = state.isLoading,
            error = state.error
        )
    }
}