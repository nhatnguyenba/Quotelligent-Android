package com.nhatnguyenba.quotelligent.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nhatnguyenba.quotelligent.data.local.entities.CollectionEntity
import com.nhatnguyenba.quotelligent.data.local.entities.QuoteEntity
import com.nhatnguyenba.quotelligent.domain.model.Quote
import com.nhatnguyenba.quotelligent.domain.usecase.AddToCollectionUseCase
import com.nhatnguyenba.quotelligent.domain.usecase.CreateCollectionUseCase
import com.nhatnguyenba.quotelligent.domain.usecase.GetCollectionsUseCase
import com.nhatnguyenba.quotelligent.domain.usecase.GetFavoriteQuotesUseCase
import com.nhatnguyenba.quotelligent.domain.usecase.GetQuotesInCollectionUseCase
import com.nhatnguyenba.quotelligent.domain.usecase.IsQuoteFavoriteUseCase
import com.nhatnguyenba.quotelligent.domain.usecase.RemoveFromCollectionUseCase
import com.nhatnguyenba.quotelligent.domain.usecase.RemoveFromFavoriteUseCase
import com.nhatnguyenba.quotelligent.domain.usecase.ToggleFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuoteViewModel @Inject constructor(
    private val isQuoteFavoriteUseCase: IsQuoteFavoriteUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase,
    private val getFavoriteQuotesUseCase: GetFavoriteQuotesUseCase,
    private val createCollectionUseCase: CreateCollectionUseCase,
    private val addToCollectionUseCase: AddToCollectionUseCase,
    private val getCollectionsUseCase: GetCollectionsUseCase,
    private val getQuotesInCollectionUseCase: GetQuotesInCollectionUseCase,
    private val removeFromCollectionUseCase: RemoveFromCollectionUseCase,
    private val removeFromFavoriteUseCase: RemoveFromFavoriteUseCase
) : ViewModel() {

    private val _favoriteQuotes = getFavoriteQuotesUseCase()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())
    val favoriteQuotes: StateFlow<List<QuoteEntity>> = _favoriteQuotes

    private val _collections = getCollectionsUseCase()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())
    val collections: StateFlow<List<CollectionEntity>> = _collections

    private val _isFavorite = MutableStateFlow(false)
    val isFavorite: StateFlow<Boolean> = _isFavorite

    suspend fun updateFavoriteStatus(quote: Quote) {
//        _isFavorite.value = isQuoteFavoriteUseCase(quote)
    }

//    suspend fun isQuoteFavorite(quote: Quote): Boolean {
////        return isQuoteFavoriteUseCase(quote)
//    }

//    fun toggleFavorite(quote: Quote) {
//        viewModelScope.launch {
//            toggleFavoriteUseCase(quote)
//            updateFavoriteStatus(quote)
//        }
//    }

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

    fun getQuotesInCollection(collectionId: Int): Flow<List<QuoteEntity>> {
        return getQuotesInCollectionUseCase(collectionId)
    }

    fun removeFromCollection(quote: QuoteEntity, collectionId: Int) {
        viewModelScope.launch {
            removeFromCollectionUseCase(quote, collectionId)
        }
    }

    fun removeFavoriteQuote(quoteId: String) {
        viewModelScope.launch {
            removeFromFavoriteUseCase(quoteId)
        }
    }
}