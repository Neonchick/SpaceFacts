package kuznetsov.hse.kmm.android.ui

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.ui.graphics.Color
import kuznetsov.hse.kmm.android.R

enum class BottomTabs(
    @StringRes
    val title: Int,
    @DrawableRes
    val icon: Int,
    val color: Color,
    val route: String,
) {
    NEW(
        title = R.string.new_page,
        icon = R.drawable.ic_baseline_cloud_24,
        color = Color(0, 203, 255),
        route = "new"
    ),
    SAVED(
        title = R.string.saved_page,
        icon = R.drawable.ic_baseline_favorite_24,
        color = Color(233, 30, 99),
        route = "saved")
}