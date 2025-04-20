package com.nhatnguyenba.quotelligent.presentation.screen.home

import com.nhatnguyenba.quotelligent.domain.model.Quote

data class QuotePageData(
    val quote: Quote,
    val backgroundUrl: String,
    val isLoading: Boolean
)