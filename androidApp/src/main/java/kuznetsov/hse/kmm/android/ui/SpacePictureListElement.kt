package kuznetsov.hse.kmm.android.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import kuznetsov.hse.kmm.SpacePictureVO
import kuznetsov.hse.kmm.android.AndroidViewModel
import kuznetsov.hse.kmm.android.R

@ExperimentalMaterialApi
@Composable
fun spacePictureListElement(
    model: AndroidViewModel,
    spacePicture: SpacePictureVO,
    navController: NavHostController,
) {
    Card(
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier.padding(10.dp),
        onClick = { navController.navigate("fullInfo/${spacePicture.title}") },
    ) {
        Column(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth()
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(
                    text = spacePicture.title,
                    fontSize = 22.sp,
                    textAlign = TextAlign.Left,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.fillMaxWidth(0.8f),
                )

                val iconId = if (spacePicture.isSaved) {
                    R.drawable.ic_baseline_favorite_24
                } else {
                    R.drawable.ic_baseline_favorite_border_24
                }
                IconToggleButton(
                    checked = spacePicture.isSaved,
                    onCheckedChange = { isSaved ->
                        if (isSaved) {
                            model.sharedViewModel.savePicture(spacePicture)
                        } else {
                            model.sharedViewModel.deletePicture(spacePicture)
                        }
                    },
                    modifier = Modifier.fillMaxWidth(0.8f)
                ) {
                    Icon(
                        painter = painterResource(iconId),
                        contentDescription = "favoriteIcon",
                        tint = Color(233, 30, 99),
                    )
                }
            }
            Image(
                painter = rememberImagePainter(spacePicture.url),
                contentDescription = "Space picture",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .padding(top = 20.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .fillMaxWidth()
                    .height(230.dp)
            )
        }
    }
}