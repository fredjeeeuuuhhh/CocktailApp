package com.example.cocktailapp.ui.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

@Composable
fun BottomNavigationBar(
    currentRoute: String,
    menuItems: Array<NavigationMenuItem>,
) {
    NavigationBar() {
        menuItems.forEach { screen ->
            NavigationBarItem(
                icon = { Icon(imageVector = screen.icon, contentDescription = null) },
                label = { Text(stringResource(screen.title)) },
                selected = currentRoute == screen.route,
                onClick = screen.navigationAction,
            )
        }
    }
}
