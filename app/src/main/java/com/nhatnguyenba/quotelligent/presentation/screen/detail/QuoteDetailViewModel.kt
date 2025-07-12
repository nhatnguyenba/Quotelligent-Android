package com.nhatnguyenba.quotelligent.presentation.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nhatnguyenba.quotelligent.domain.model.Quote
import com.nhatnguyenba.quotelligent.domain.usecase.GetAuthorQuotesUseCase
import com.nhatnguyenba.quotelligent.domain.usecase.GetCategoryQuotesUseCase
import com.nhatnguyenba.quotelligent.domain.usecase.GetQuoteDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuoteDetailViewModel @Inject constructor(
    private val getQuoteDetailUseCase: GetQuoteDetailUseCase
) : ViewModel() {
    private val _quote = MutableStateFlow<Quote?>(null)
    val quote: StateFlow<Quote?> = _quote

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun loadQuote(quoteId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _quote.value = getQuoteDetailUseCase(quoteId)
            _isLoading.value = false
        }
    }
}