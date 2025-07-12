package com.nhatnguyenba.quotelligent.presentation.screen.detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.nhatnguyenba.quotelligent.presentation.component.QuoteItem
import com.nhatnguyenba.quotelligent.presentation.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthorDetailScreen(
    viewModel: AuthorDetailViewModel = hiltViewModel(),
    authorName: String,
    onBack: () -> Unit,
    navController: NavController
) {
    val quotes by viewModel.quotes.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    LaunchedEffect(authorName) {
        viewModel.loadQuotes(authorName)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Author: $authorName") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
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
        } else {
            if (quotes.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    contentAlignment = Alignment.Center
                ) {
                    Text("No quotes found for this author")
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .padding(16.dp)
                ) {
                    items(quotes.size) { index ->
                        QuoteItem(quote = quotes[index], onClick = {
                            navController.navigate("${Screen.QuoteDetail.route}/${quotes[index].id}")
                        })
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
        }
    }
}

//@Composable
//fun QuoteItem(quote: Quote) {
//    Card(
//        modifier = Modifier.fillMaxWidth(),
//        elevation = CardDefaults.cardElevation(4.dp),
//        shape = RoundedCornerShape(8.dp)
//    ) {
//        Column(modifier = Modifier.padding(16.dp)) {
//            Text(
//                text = "\"${quote.content}\"",
//                style = MaterialTheme.typography.bodyLarge
//            )
//            Spacer(modifier = Modifier.height(8.dp))
//            Text(
//                text = "- ${quote.author} -",
//                style = MaterialTheme.typography.bodyMedium,
//                fontWeight = FontWeight.Bold,
//                textAlign = TextAlign.End,
//                modifier = Modifier.fillMaxWidth()
//            )
//        }
//    }
//}