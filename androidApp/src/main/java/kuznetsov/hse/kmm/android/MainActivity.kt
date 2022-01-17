package kuznetsov.hse.kmm.android

import android.os.Bundle
import android.widget.TextView
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kuznetsov.hse.kmm.Greeting
import kuznetsov.hse.kmm.ViewState
import kuznetsov.hse.kmm.android.ui.ContentView
import kuznetsov.hse.kmm.database.DatabaseInitializer

class MainActivity : AppCompatActivity() {

    private val model: AndroidViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val state = mutableStateOf(
            ViewState(
                title = Greeting().greeting(),
                explanation = "",
                url = "",
            )
        )

        setContent {
            ContentView(state)
        }

        lifecycleScope.launch {
            model.sharedViewModel.stateFlow.onEach { viewState ->
                state.value = viewState
            }.launchIn(this)
            model.sharedViewModel.getPictureTitle()
        }

        val database = DatabaseInitializer().database
    }

    private fun greet(): String {
        return Greeting().greeting()
    }
}
