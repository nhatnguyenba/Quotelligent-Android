package com.nhatnguyenba.quotelligent.presentation.component

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

@Composable
fun AutoResizeText(
    text: String,
    modifier: Modifier = Modifier,
    style: TextStyle = MaterialTheme.typography.displayMedium,
    color: Color = Color.White,
    textAlign: TextAlign = TextAlign.Center,
    minTextSize: TextUnit = 12.sp,
    stepGranularity: TextUnit = 1.sp
) {
    var textSize by remember { mutableStateOf(style.fontSize) }

    Text(
        text = text,
        modifier = modifier,
        style = style.copy(fontSize = textSize),
        color = color,
        textAlign = textAlign,
        maxLines = Int.MAX_VALUE,
        onTextLayout = { layoutResult ->
            // nếu vẫn overflow thì giảm tiếp
            if (layoutResult.hasVisualOverflow) {
                val newSize = (textSize.value - stepGranularity.value).sp
                if (newSize >= minTextSize) {
                    textSize = newSize
                }
            }
        }
    )
}