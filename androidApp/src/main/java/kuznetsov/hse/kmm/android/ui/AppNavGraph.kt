package kuznetsov.hse.kmm.android.ui

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import kuznetsov.hse.kmm.android.AndroidViewModel

@ExperimentalMaterialApi
@Composable
fun appNavGraph(
    model: AndroidViewModel,
    modifier: Modifier = Modifier,
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        modifier = modifier,
        startDestination = "new"
    ) {

        composable("new") {
            val pictures =
                model.sharedViewModel.picturesFromNet.collectAsState().value.values.toList()
            spacePictureList(
                model = model,
                spacePictures = pictures,
                navController = navController,
            )
        }

        composable("saved") {
            val pictures =
                model.sharedViewModel.picturesFromDB.collectAsState().value.values.toList()
            spacePictureList(
                model = model,
                spacePictures = pictures,
                navController = navController,
            )
        }

        composable(
            route = "fullInfo/{title}",
            arguments = listOf(navArgument(name = "title") { NavType.StringType })
        ) { backStackEntry ->
            val title = backStackEntry.arguments?.getString("title") ?: ""
            val picture = model.sharedViewModel.picturesFromNet.collectAsState().value[title]
                ?: model.sharedViewModel.picturesFromDB.collectAsState().value[title]
            picture?.let { picture ->
                spacePictureFullInfo(
                    model = model,
                    spacePicture = picture,
                )
            }
        }
    }
}