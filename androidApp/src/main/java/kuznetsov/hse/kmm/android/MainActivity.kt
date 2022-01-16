package kuznetsov.hse.kmm.android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kuznetsov.hse.kmm.Greeting
import android.widget.TextView
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kuznetsov.hse.kmm.database.DatabaseInitializer

class MainActivity : AppCompatActivity() {

    private val model: AndroidViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tv: TextView = findViewById(R.id.text_view)
        tv.text = greet()

        lifecycleScope.launch {
            model.sharedViewModel.stateFlow.onEach {
                tv.text = it.title
            }.launchIn(this)
            model.sharedViewModel.getPictureTitle()
        }

        val database = DatabaseInitializer().database
    }

    private fun greet(): String {
        return Greeting().greeting()
    }
}
