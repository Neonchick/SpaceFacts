package kuznetsov.hse.kmm.android.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import kuznetsov.hse.kmm.ViewState

@Composable
fun ContentView(
    state: MutableState<ViewState>
) {
    val viewStateRemember = remember { state }
    Column(
        modifier = Modifier
            .padding(20.dp)
            .verticalScroll(rememberScrollState())
            .fillMaxWidth()
    ) {
        Text(
            text = viewStateRemember.value.title,
            fontSize = 22.sp,
            textAlign = TextAlign.Center,
            fontWeight= FontWeight.Bold,
            modifier = Modifier.fillMaxWidth(1f),
        )
        Image(
            painter = rememberImagePainter(viewStateRemember.value.url),
            contentDescription = null,
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .height(260.dp)
                .padding(top = 20.dp)
        )
        Text(
            text = viewStateRemember.value.explanation,
            fontSize = 16.sp,
            modifier = Modifier.padding(top = 20.dp)
        )
    }
}