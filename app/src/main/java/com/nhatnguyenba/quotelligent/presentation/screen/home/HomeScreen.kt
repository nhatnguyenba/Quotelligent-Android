package com.nhatnguyenba.quotelligent.presentation.screen.home

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Paint
import android.graphics.Picture
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.draw
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.painter.BrushPainter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.nhatnguyenba.quotelligent.R
import com.nhatnguyenba.quotelligent.presentation.component.AutoResizeText
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream

@Composable
fun HomeScreen(viewModel: HomeViewModel = hiltViewModel()) {
    val maxPageCount = Short.MAX_VALUE.toInt()
    val pagerState = rememberPagerState(
        initialPage = maxPageCount / 2,
        pageCount = { viewModel.cachedPages.size }
    )

    LaunchedEffect(pagerState.currentPage) {
        viewModel.checkAndPreload(pagerState.currentPage)
    }

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    VerticalPager(
        state = pagerState,
        modifier = Modifier.fillMaxSize(),
    ) { page ->
        Log.d("NHAT", "Page: $page")
        val actualPage = page % viewModel.cachedPages.size
        val quoteData = viewModel.cachedPages[actualPage]

        val picture = remember { Picture() }
        Column(
            modifier = Modifier
                .padding(0.dp)
                .fillMaxSize()
                .drawWithCache {
                    // Example that shows how to redirect rendering to an Android Picture and then
                    // draw the picture into the original destination
                    val width = this.size.width.toInt()
                    val height = this.size.height.toInt()
                    onDrawWithContent {
                        val pictureCanvas =
                            androidx.compose.ui.graphics.Canvas(
                                picture.beginRecording(
                                    width,
                                    height
                                )
                            )
                        draw(this, this.layoutDirection, pictureCanvas, this.size) {
                            this@onDrawWithContent.drawContent()
                        }
                        picture.endRecording()

                        drawIntoCanvas { canvas -> canvas.nativeCanvas.drawPicture(picture) }
                    }
                }
        ) {
            // Screen Content To Capture
            Box(modifier = Modifier.fillMaxSize()) {
                AsyncImage(
                    model = quoteData.backgroundUrl,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    error = randomGradientPainter(),
                    placeholder = randomGradientPainter(),
                    modifier = Modifier
                        .fillMaxSize()
                        .alpha(0.9f)
                )

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    AutoResizeText(
                        text = quoteData.quote.content ?: "Unknown",
                        style = MaterialTheme.typography.displayMedium,
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    AutoResizeText(
                        text = "- ${quoteData.quote.author ?: "Unknown"} -",
                        style = MaterialTheme.typography.headlineMedium,
                        color = Color.White.copy(alpha = 0.8f)
                    )
                }

                // Action Buttons
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp)
                        .align(Alignment.BottomCenter),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    IconButton(onClick = { }) {
                        Icon(
                            imageVector = Icons.Outlined.Favorite,
                            contentDescription = "Favorite",
                            tint = Color.White
                        )
                    }

                    IconButton(onClick = {
                        // share quote to social media
                        coroutineScope.launch {
                            val bitmap = createBitmapFromPicture(picture)
                            shareQuoteImage(context, bitmap, quoteData)
                        }
                    }) {
                        Icon(Icons.Default.Share, "Share", tint = Color.White)
                    }

                    IconButton(onClick = { }) {
                        Icon(
                            painter = painterResource(R.drawable.ic_bookmark),
                            "Save",
                            tint = Color.White
                        )
                    }
                }
            }
        }
    }
}

private fun createBitmapFromPicture(picture: Picture): Bitmap {
    val bitmap = Bitmap.createBitmap(
        picture.width,
        picture.height,
        Bitmap.Config.ARGB_8888
    )

    val targetBmp: Bitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true)
    val canvas = android.graphics.Canvas(bitmap)
    canvas.drawColor(android.graphics.Color.WHITE)
    canvas.drawBitmap(targetBmp, 0f, 0f, Paint())
    return targetBmp
}

fun shareQuoteImage(context: Context, bitmap: Bitmap, quoteData: QuotePageData) {
    try {
        // Save bitmap to cache
        val cachePath = File(context.cacheDir, "images")
        cachePath.mkdirs()
        val file = File(cachePath, "quote_${System.currentTimeMillis()}.png")
        FileOutputStream(file).use { out ->
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)
        }

        // Get content URI using FileProvider
        val contentUri: Uri = FileProvider.getUriForFile(
            context,
            "${context.packageName}.fileprovider",
            file
        )

        // Create share intent
        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            type = "image/png"
            putExtra(Intent.EXTRA_STREAM, contentUri)
            putExtra(
                Intent.EXTRA_TEXT,
                "${quoteData.quote.content}\n\n- ${quoteData.quote.author} -"
            )
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }

        // Start share activity
        context.startActivity(Intent.createChooser(shareIntent, "Share Quote"))
    } catch (e: Exception) {
        e.printStackTrace()
        Toast.makeText(context, "Error sharing quote", Toast.LENGTH_SHORT).show()
    }
}

// Định nghĩa 2 danh sách màu sáng và tối
private val lightColors = listOf(
    Color(0xFFFFF8E1), // amber50
    Color(0xFFFFF3E0), // orange50
    Color(0xFFE8F5E9), // green50
    Color(0xFFE3F2FD)  // blue50
)

private val darkColors = listOf(
    Color(0xFF263238), // blueGrey900
    Color(0xFF1A237E), // indigo900
    Color(0xFF3E2723), // brown900
    Color(0xFF212121)  // grey900
)

@Composable
fun randomGradientPainter(): Painter {
    // Lấy timestamp làm seed
    val now = System.currentTimeMillis()
    // Chọn category dựa trên chẵn/lẻ của now
    val palette = darkColors
    // Tính 2 index khác nhau trong palette
    val idx1 = (now % palette.size).toInt()
    val idx2 = ((now / 1000L) % palette.size).toInt()
    // Nhớ lại 2 màu này trong composition
    val colors = remember(palette, idx1, idx2) {
        listOf(palette[idx1], palette[idx2])
    }

    return remember {
        BrushPainter(
            Brush.linearGradient(
                colors = colors,
                start = Offset(0f, Float.POSITIVE_INFINITY),
                end = Offset(Float.POSITIVE_INFINITY, 0f)
            )
        )
    }
}