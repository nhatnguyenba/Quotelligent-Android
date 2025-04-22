package com.nhatnguyenba.quotelligent.presentation.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

//@Composable
//private fun SearchHistory(history: List<String>) {
//    Column {
//        Text("Recent Searches", style = MaterialTheme.typography.titleMedium)
//        LazyRow {
//            items(history) { term ->
//                SuggestionChip(
//                    onClick = { viewModel.onQueryChange(term) },
//                    label = { Text(term) }
//                )
//            }
//        }
//    }
//}