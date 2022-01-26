package kuznetsov.hse.kmm.android.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.rememberNavController
import kuznetsov.hse.kmm.SpacePictureVO
import kuznetsov.hse.kmm.android.AndroidViewModel

@ExperimentalMaterialApi
@Composable
fun appContent(model: AndroidViewModel) {
    val tabs = remember { BottomTabs.values() }
    val navController = rememberNavController()
    Scaffold(
        backgroundColor = Color.White,
        bottomBar = { bottomBar(navController = navController, tabs) }
    ) { innerPaddingModifier ->
        appNavGraph(
            navController = navController,
            model = model,
            modifier = Modifier.padding(innerPaddingModifier)
        )
    }
}