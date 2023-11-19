package com.example.cocktailapp.ui.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource

@Composable
fun BottomNavigationBar(
    currentRoute: String,
    menuItems: Array<NavigationMenuItem>,
) {
    NavigationBar(
        containerColor = Color(0xFF89c7bc),
    ) {
        menuItems.forEach { screen ->
            NavigationBarItem(
                icon = { Icon(imageVector = screen.icon, contentDescription = null) },
                label = { Text(stringResource(screen.title)) },
                selected = currentRoute == screen.route,
                onClick = screen.navigationAction,
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.White,
                    selectedTextColor = Color.White,
                    indicatorColor = Color(0xFF6d9f96),
                    unselectedIconColor = Color.White,
                    unselectedTextColor = Color.White,
                ),
            )
        }
    }
}
