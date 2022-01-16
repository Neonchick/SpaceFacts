package kuznetsov.hse.kmm.android

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kuznetsov.hse.kmm.MpViewModel

class AndroidViewModel : ViewModel() {

    val sharedViewModel by lazy { MpViewModel() }

    init {
        viewModelScope.launch {}
    }

}