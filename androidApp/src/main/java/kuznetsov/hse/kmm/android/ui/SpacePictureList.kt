package kuznetsov.hse.kmm.android.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import kuznetsov.hse.kmm.SpacePictureVO
import kuznetsov.hse.kmm.android.AndroidViewModel

@ExperimentalMaterialApi
@Composable
fun spacePictureList(
    model: AndroidViewModel,
    spacePictures: List<SpacePictureVO>,
    navController: NavHostController,
) {
    LazyColumn(modifier = Modifier.fillMaxWidth()) {
        items(spacePictures) { spacePicture ->
            spacePictureListElement(
                model = model,
                spacePicture = spacePicture,
                navController = navController,
            )
        }
    }
}