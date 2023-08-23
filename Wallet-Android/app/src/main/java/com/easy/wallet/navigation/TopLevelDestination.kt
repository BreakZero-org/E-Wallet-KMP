package com.easy.wallet.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Store
import androidx.compose.material.icons.filled.TravelExplore
import androidx.compose.material.icons.twotone.Home
import androidx.compose.material.icons.twotone.Store
import androidx.compose.material.icons.twotone.TravelExplore
import androidx.compose.ui.graphics.vector.ImageVector
import com.easy.wallet.R
import com.easy.wallet.design.UiText

enum class TopLevelDestination(
    val title: UiText,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val hasNews: Boolean = false,
    val badgeCount: Int? = null
) {
     HOME(
         title = UiText.StringResource(resId = R.string.tab_home),
         selectedIcon = Icons.TwoTone.Home,
         unselectedIcon = Icons.Default.Home
     ),
     MARKETPLACE(
         title = UiText.StringResource(resId = R.string.tab_marketplace),
         selectedIcon = Icons.TwoTone.Store,
         unselectedIcon = Icons.Default.Store
     ),
     DISCOVER(
         title = UiText.StringResource(resId = R.string.tab_discover),
         selectedIcon = Icons.TwoTone.TravelExplore,
         unselectedIcon = Icons.Default.TravelExplore
     )
}