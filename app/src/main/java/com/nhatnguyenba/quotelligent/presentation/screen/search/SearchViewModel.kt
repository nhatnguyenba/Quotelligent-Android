package com.nhatnguyenba.quotelligent.presentation.screen.search

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nhatnguyenba.quotelligent.domain.usecase.SearchAuthorUseCase
import com.nhatnguyenba.quotelligent.domain.usecase.SearchCategoryUseCase
import com.nhatnguyenba.quotelligent.domain.usecase.SearchQuoteUseCase
import com.nhatnguyenba.quotelligent.presentation.component.SearchFilter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchQuote: SearchQuoteUseCase,
    private val searchCategory: SearchCategoryUseCase,
    private val searchAuthor: SearchAuthorUseCase
) : ViewModel() {

    private val _state = mutableStateOf(SearchState())
    val state: State<SearchState> = _state

    fun onQueryChange(query: String) {
        _state.value = _state.value.copy(query = query)
        performSearch()
        Log.d("NHAT", "onQueryChange: $query")
    }

    fun onFilterSelected(filter: SearchFilter) {
        _state.value = _state.value.copy(selectedFilter = filter)
        performSearch()
        Log.d("NHAT", "onFilterSelected: $filter")
    }

    private fun performSearch() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            _state.value = withContext(Dispatchers.IO) {
                try {
                    val results = when (_state.value.selectedFilter) {
                        SearchFilter.QUOTES -> {
                            searchQuote(_state.value.query)
                        }

                        SearchFilter.AUTHORS -> {
                            searchAuthor(_state.value.query)
                        }

                        SearchFilter.CATEGORIES -> {
                            searchCategory(_state.value.query)
                        }

                        SearchFilter.BOOKMARKS -> {
                            null
                        }
                    }
                    _state.value.copy(
                        results = results ?: emptyList(),
                        selectedFilter = _state.value.selectedFilter,
                        isLoading = false,
                        error = null
                    )

                } catch (e: Exception) {
                    _state.value.copy(
                        error = e.message ?: "Error searching",
                        isLoading = false
                    )
                }
            }
        }
    }
}

data class SearchState(
    val query: String = "",
    val selectedFilter: SearchFilter = SearchFilter.QUOTES,
    val results: List<Any> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)