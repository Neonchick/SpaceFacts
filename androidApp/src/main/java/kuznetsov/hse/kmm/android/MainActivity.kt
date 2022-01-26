package kuznetsov.hse.kmm.android

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kuznetsov.hse.kmm.Greeting
import kuznetsov.hse.kmm.SpacePictureVO
import kuznetsov.hse.kmm.android.ui.appContent

class MainActivity : AppCompatActivity() {

    private val model: AndroidViewModel by viewModels()

    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            appContent(model)
//            ContentView(state)
        }

        lifecycleScope.launch {
            model.sharedViewModel.getPicturesFromNet()
        }
    }

}
