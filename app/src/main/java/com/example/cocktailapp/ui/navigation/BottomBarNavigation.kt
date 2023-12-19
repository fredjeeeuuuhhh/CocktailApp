package com.example.cocktailapp.ui.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.example.cocktailapp.R

@Composable
fun NavigationDrawerContent(
    currentRoute: String,
    menuItems: Array<NavigationMenuItem>,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        menuItems.forEach {screen->
            NavigationDrawerItem(
                selected = screen.route == currentRoute,
                label = {
                    Text(
                        text = stringResource(screen.title),
                        modifier = Modifier.padding(horizontal = dimensionResource(R.dimen.drawer_padding_header)),
                    )
                },
                icon = {
                    Icon(
                        imageVector = screen.icon,
                        contentDescription = stringResource(screen.title),
                    )
                },
                colors = NavigationDrawerItemDefaults.colors(
                    unselectedContainerColor = Color.Transparent,
                ),
                onClick = screen.navigationAction,
            )
        }
    }
}