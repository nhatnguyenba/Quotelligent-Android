package com.nhatnguyenba.quotelligent.presentation.screen.collection

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.nhatnguyenba.quotelligent.data.local.entities.CollectionEntity
import com.nhatnguyenba.quotelligent.data.local.entities.QuoteEntity
import com.nhatnguyenba.quotelligent.presentation.component.CollectionItem
import com.nhatnguyenba.quotelligent.presentation.component.FavoriteCollectionItem
import com.nhatnguyenba.quotelligent.presentation.viewmodel.QuoteViewModel

@Composable
fun CollectionScreen() {
    val viewModel: QuoteViewModel = hiltViewModel()
    val favoriteQuotes by viewModel.favoriteQuotes.collectAsState()
    val collections by viewModel.collections.collectAsState()

    var selectedCollection by remember { mutableStateOf<CollectionEntity?>(null) }
    val quotesInCollection by viewModel.getQuotesInCollection(selectedCollection?.id ?: 0)
        .collectAsState(initial = emptyList())

    if (selectedCollection != null) {
        CollectionDetailScreen(
            collection = selectedCollection!!,
            quotes = if (selectedCollection!!.isFavoriteCollection) favoriteQuotes else quotesInCollection,
            onBack = { selectedCollection = null },
            onRemoveQuote = { quote ->
                if (selectedCollection!!.isFavoriteCollection) {
                    viewModel.removeFavoriteQuote(quote.id)
                } else {
                    viewModel.removeFromCollection(quote, selectedCollection!!.id)
                }
            }
        )
    } else {
        CollectionListScreen(
            favoriteQuotes = favoriteQuotes,
            collections = collections,
            onCollectionSelect = {
                selectedCollection = it
            }
        )
    }
}

@Composable
fun CollectionListScreen(
    favoriteQuotes: List<QuoteEntity>,
    collections: List<CollectionEntity>,
    onCollectionSelect: (CollectionEntity) -> Unit
) {
    LazyColumn {
        // Favorites Section
//        item {
//            Text(
//                "Favorites",
//                style = MaterialTheme.typography.headlineMedium,
//                modifier = Modifier.padding(16.dp)
//            )
//        }
//
//        if (favoriteQuotes.isEmpty()) {
//            item {
//                EmptyState(
//                    icon = Icons.Filled.Favorite,
//                    text = "No favorite quotes yet"
//                )
//            }
//        } else {
//            items(favoriteQuotes.size) { index ->
//                QuoteItem(quote = favoriteQuotes[index])
//            }
//        }

        // Collections Section
        item {
            Text(
                "Collections",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(16.dp)
            )
        }

        item {
            FavoriteCollectionItem(onClick = {
                onCollectionSelect(
                    CollectionEntity(
                        name = "Favorites",
                        isFavoriteCollection = true
                    )
                )
            })
        }

        if (collections.isEmpty()) {
//            item {
//                EmptyState(
//                    icon = Icons.Filled.Folder,
//                    text = "No collections created yet"
//                )
//            }
        } else {
            items(collections.size) { index ->
                CollectionItem(
                    collection = collections[index],
                    onClick = { onCollectionSelect(collections[index]) }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CollectionDetailScreen(
    collection: CollectionEntity,
    quotes: List<QuoteEntity>,
    onBack: () -> Unit,
    onRemoveQuote: (QuoteEntity) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(collection.name) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { innerPadding ->
        if (quotes.isEmpty()) {
            EmptyState(
                icon = Icons.Filled.Info,
                text = "No quotes in this collection",
                modifier = Modifier.padding(innerPadding)
            )
        } else {
            LazyColumn(modifier = Modifier.padding(innerPadding)) {
                items(quotes.size) { index ->
                    QuoteItem(
                        quote = quotes[index],
                        onRemove = { onRemoveQuote(quotes[index]) }
                    )
                }
            }
        }
    }
}

@Composable
fun QuoteItem(quote: QuoteEntity, onRemove: (() -> Unit)? = null) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                "\"${quote.text}\"",
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(Modifier.height(8.dp))
            Text(
                "- ${quote.author} -",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.End,
                modifier = Modifier.fillMaxWidth()
            )

            onRemove?.let {
                Spacer(Modifier.height(16.dp))
                Button(
                    onClick = it,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.errorContainer,
                        contentColor = MaterialTheme.colorScheme.onErrorContainer
                    ),
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text("Remove from Collection")
                }
            }
        }
    }
}

@Composable
fun EmptyState(icon: ImageVector, text: String, modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxSize()
            .padding(32.dp)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                icon,
                contentDescription = null,
                modifier = Modifier.size(64.dp),
                tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
            )
            Spacer(Modifier.height(16.dp))
            Text(
                text,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
        }
    }
}