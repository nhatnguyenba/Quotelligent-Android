package com.nhatnguyenba.quotelligent.presentation.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nhatnguyenba.quotelligent.domain.model.Quote
import com.nhatnguyenba.quotelligent.domain.usecase.AddToCollectionUseCase
import com.nhatnguyenba.quotelligent.domain.usecase.CreateCollectionUseCase
import com.nhatnguyenba.quotelligent.domain.usecase.GetCollectionsUseCase
import com.nhatnguyenba.quotelligent.domain.usecase.GetQuoteDetailUseCase
import com.nhatnguyenba.quotelligent.domain.usecase.IsQuoteFavoriteUseCase
import com.nhatnguyenba.quotelligent.domain.usecase.IsQuoteSavedUseCase
import com.nhatnguyenba.quotelligent.domain.usecase.ToggleFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuoteDetailViewModel @Inject constructor(
    private val getQuoteDetailUseCase: GetQuoteDetailUseCase,
    private val isQuoteFavoriteUseCase: IsQuoteFavoriteUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase,
    private val isQuoteSavedUseCase: IsQuoteSavedUseCase,
    private val getCollectionsUseCase: GetCollectionsUseCase,
    private val createCollectionUseCase: CreateCollectionUseCase,
    private val addToCollectionUseCase: AddToCollectionUseCase
) : ViewModel() {

    private val _quote = MutableStateFlow<Quote?>(null)
    val quote: StateFlow<Quote?> = _quote

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    val collections = getCollectionsUseCase()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    fun loadQuote(quoteId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _quote.value = getQuoteDetailUseCase(quoteId)
            _isLoading.value = false
        }
    }

    suspend fun isQuoteFavorite(quote: Quote): Boolean {
        return isQuoteFavoriteUseCase(quote)
    }

    suspend fun isQuoteSaved(quote: Quote): Boolean {
        return isQuoteSavedUseCase(quote)
    }

    fun toggleFavorite(quote: Quote) {
        viewModelScope.launch {
            toggleFavoriteUseCase(quote)
        }
    }

    fun createCollection(name: String, onSuccess: (Int) -> Unit) {
        viewModelScope.launch {
            val id = createCollectionUseCase(name)
            onSuccess(id)
        }
    }

    fun addToCollection(quote: Quote, collectionId: Int) {
        viewModelScope.launch {
            addToCollectionUseCase(quote, collectionId)
        }
    }
}