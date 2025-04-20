package com.nhatnguyenba.quotelligent.presentation.screen.home

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nhatnguyenba.quotelligent.domain.usecase.GetBackgroundImageUseCase
import com.nhatnguyenba.quotelligent.domain.usecase.GetRandomQuoteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getRandomQuote: GetRandomQuoteUseCase,
    private val getBackgroundImage: GetBackgroundImageUseCase,
) : ViewModel() {

    private val _cachedPages = mutableStateListOf<QuotePageData>()
    val cachedPages: List<QuotePageData> get() = _cachedPages

    // Cấu hình preload
    private val preloadWindow = 3 // Preload khi cách biệt preloadWindow trang
    private val initialLoadCount = 7 // Load ban đầu initialLoadCount trang
    private val preloadBatchSize = 7 // Mỗi lần preload preloadBatchSize trang
    private val mutex = Mutex()

    init {
        viewModelScope.launch {
            loadInitialPages()
        }
    }

    fun checkAndPreload(currentPage: Int) {
        viewModelScope.launch {
            val remainingPages = _cachedPages.lastIndex - currentPage
            if (remainingPages <= preloadWindow) {
                preloadMorePages()
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private suspend fun loadInitialPages() {
        (1..initialLoadCount).asFlow()
            .flatMapMerge(concurrency = initialLoadCount) { index ->
                flow {
                    val page = loadSinglePage()
                    emit(page)
                }.flowOn(Dispatchers.IO)
            }
            .buffer()
            .collect { pageData ->
                _cachedPages.add(pageData)
            }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private suspend fun preloadMorePages() = mutex.withLock {
        (1..preloadBatchSize).asFlow()
            .flatMapMerge(concurrency = initialLoadCount) { index ->
                flow {
                    val page = loadSinglePage()
                    emit(page)
                }.flowOn(Dispatchers.IO)
            }
            .buffer()
            .collect { pageData ->
                _cachedPages.add(pageData)
            }

        // Giữ số lượng trang cache không vượt quá giới hạn
        if (_cachedPages.size > initialLoadCount * 2) {
            _cachedPages.removeAt(0)
        }
    }

    private suspend fun loadSinglePage(): QuotePageData {
        val quote = getRandomQuote()
        val backgroundUrl = getBackgroundImage(quote.tags ?: listOf("inspiration"))
        return QuotePageData(
            quote = quote,
            backgroundUrl = backgroundUrl,
            isLoading = false
        )
    }
}