package com.example.cocktailapp.ui.navigation

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.vector.ImageVector

data class NavigationMenuItem(
    @StringRes val title: Int,
    val route: String,
    val icon: ImageVector,
    val navigationAction: () -> Unit
)
