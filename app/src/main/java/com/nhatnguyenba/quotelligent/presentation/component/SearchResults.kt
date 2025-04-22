package com.nhatnguyenba.quotelligent.presentation.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.nhatnguyenba.quotelligent.R
import com.nhatnguyenba.quotelligent.domain.model.Author
import com.nhatnguyenba.quotelligent.domain.model.Category
import com.nhatnguyenba.quotelligent.domain.model.Quote
import kotlin.math.abs

@Composable
fun SearchResults(
    results: List<Any>,
    type: SearchFilter,
    isLoading: Boolean,
    error: String?
) {
    when {
        isLoading -> LoadingIndicator()
        error != null -> ErrorMessage(error)
        results.isEmpty() -> EmptyResults()
        else -> ResultsList(results, type)
    }
}

@Composable
private fun ResultsList(
    results: List<Any>, // Có thể là List<Quote>, List<Author> hoặc List<Category>
    type: SearchFilter
) {
    val gridCells = if (type == SearchFilter.QUOTES) {
        GridCells.Fixed(1)
    } else {
        GridCells.Adaptive(minSize = 150.dp)
    }

    LazyVerticalGrid(
        columns = gridCells,
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(results.size) { index ->
            when (type) {
                SearchFilter.QUOTES -> QuoteItem(quote = results[index] as Quote, onClick = {})
                SearchFilter.AUTHORS -> AuthorItem(
                    author = results[index] as Author,
                    onClick = {})

                SearchFilter.CATEGORIES -> CategoryItem(
                    category = results[index] as Category,
                    onClick = {})

                SearchFilter.BOOKMARKS -> {}
            }
        }
    }
}

@Composable
fun QuoteItem(quote: Quote, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { /* Handle click */ },
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = quote.content ?: "",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "- ${quote.author ?: "Unknown"} -",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun AuthorItem(author: Author, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1.5f)
            .clickable { /* Xử lý click */ },
        elevation = CardDefaults.cardElevation(4.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            AsyncImage(
                model = author.imageUrl,
                contentDescription = null,
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape),
                placeholder = painterResource(R.drawable.ic_face),
                error = painterResource(R.drawable.ic_face)
            )

            Column {
                Text(
                    text = author.name ?: "Unkown",
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "${author.count} quotes",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
private fun CategoryItem(category: Category, onClick: () -> Unit) {
    val categoryColor = remember(category.name) {
        // Tạo màu dựa trên tên category
        val colors = listOf(
            Color(0xFFFFF3E0), // Light Orange
            Color(0xFFE8F5E9), // Light Green
            Color(0xFFE3F2FD), // Light Blue
            Color(0xFFFCE4EC)  // Light Pink
        )
        colors[abs(category.name.hashCode()) % colors.size]
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .clickable { },
        elevation = CardDefaults.cardElevation(4.dp),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = categoryColor.copy(alpha = 0.8f)
        )
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = when (category.name?.lowercase()) {
                    "love" -> Icons.Default.Favorite
                    "life" -> ImageVector.vectorResource(R.drawable.ic_spa)
                    "success" -> Icons.Default.Star
                    else -> ImageVector.vectorResource(R.drawable.ic_category)
                },
                contentDescription = null,
                modifier = Modifier.size(32.dp),
                tint = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = category.name?.replaceFirstChar { it.titlecaseChar() } ?: "Unkown",
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
