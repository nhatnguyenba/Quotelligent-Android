package com.nhatnguyenba.quotelligent.presentation.screen.detail

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.outlined.Bookmark
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.nhatnguyenba.quotelligent.presentation.component.CollectionSelectionDialog

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun QuoteDetailScreen(
    viewModel: QuoteDetailViewModel = hiltViewModel(),
    quoteId: String,
    onBack: () -> Unit
) {
    val quote by viewModel.quote.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    var isFavorite by remember { mutableStateOf(false) }
    var isSaved by remember { mutableStateOf(false) }
    val context = LocalContext.current
    var showCollectionDialog by remember { mutableStateOf(false) }

    // Load quote and check favorite/saved status
    LaunchedEffect(quoteId) {
        viewModel.loadQuote(quoteId)
        quote?.let {
            isFavorite = viewModel.isQuoteFavorite(it)
            isSaved = viewModel.isQuoteSaved(it)
        }
    }

    // Update states when quote changes
    LaunchedEffect(quote) {
        quote?.let {
            isFavorite = viewModel.isQuoteFavorite(it)
            isSaved = viewModel.isQuoteSaved(it)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Quote Detail") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    // Favorite Button
                    IconButton(
                        onClick = {
                            quote?.let {
                                viewModel.toggleFavorite(it)
                                isFavorite = !isFavorite
                            }
                        }
                    ) {
                        Icon(
                            imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.Favorite,
                            contentDescription = "Favorite",
                            tint = if (isFavorite) Color.Red else MaterialTheme.colorScheme.onSurface
                        )
                    }

                    // Share Button
                    IconButton(
                        onClick = {
                            quote?.let {
                                val shareText = "\"${it.content}\"\n\n- ${it.author} -"
                                val shareIntent = Intent(Intent.ACTION_SEND).apply {
                                    type = "text/plain"
                                    putExtra(Intent.EXTRA_TEXT, shareText)
                                }
                                context.startActivity(
                                    Intent.createChooser(
                                        shareIntent,
                                        "Share Quote"
                                    )
                                )
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Share,
                            contentDescription = "Share",
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }

                    // Save to Collection Button
                    IconButton(
                        onClick = {
                            quote?.let {
                                showCollectionDialog = true
                            }
                        }
                    ) {
                        Icon(
                            imageVector = if (isSaved) Icons.Filled.Bookmark else Icons.Outlined.Bookmark,
                            contentDescription = "Save to Collection",
                            tint = if (isSaved) Color.Blue else MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else if (quote != null) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp)
            ) {
                Text(
                    text = "\"${quote?.content}\"",
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                Text(
                    text = "- ${quote?.author} -",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.End,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))

                // Tags
                Text(
                    text = "Categories:",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    quote?.tags?.forEach { tag ->
                        AssistChip(
                            onClick = { /* Handle tag click */ },
                            label = { Text(tag) },
                            colors = AssistChipDefaults.assistChipColors(
                                containerColor = MaterialTheme.colorScheme.primaryContainer
                            )
                        )
                    }
                }
            }
        } else {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                Text("Quote not found")
            }
        }

        // Collection Selection Dialog
        if (showCollectionDialog) {
            quote?.let { currentQuote ->
                CollectionSelectionDialog(
                    collections = viewModel.collections.collectAsState().value,
                    onSelectCollection = { collection ->
                        viewModel.addToCollection(currentQuote, collection.id)
                        isSaved = true
                        showCollectionDialog = false
                        Toast.makeText(context, "Added to the \'${collection.name}\' collection", Toast.LENGTH_SHORT).show()
                    },
                    onCreateCollection = { name ->
                        viewModel.createCollection(name) { collectionId ->
                            viewModel.addToCollection(currentQuote, collectionId.toInt())
                            isSaved = true
                            showCollectionDialog = false
                            Toast.makeText(
                                context,
                                "Created and added to the \'${name}\' collection",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    },
                    onDismiss = { showCollectionDialog = false }
                )
            }
        }
    }
}