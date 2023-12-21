package com.example.cocktailapp.ui.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource

@Composable
fun TaskNavigationRail(currentRoute: String, menuItems: Array<NavigationMenuItem>, modifier: Modifier = Modifier) {
    NavigationRail(modifier = modifier) {
        menuItems.forEach { screen ->
            NavigationRailItem(
                selected = screen.route == currentRoute,
                onClick = screen.navigationAction,
                icon = {
                    Icon(
                        imageVector = screen.icon,
                        contentDescription = stringResource(screen.title),
                    )
                },
            )
        }
    }
}
