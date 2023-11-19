package com.example.cocktailapp.ui.cocktails.cocktailoverview

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.cocktailapp.model.Cocktail
import com.example.cocktailapp.ui.cocktails.cocktailoverview.components.Cocktails

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CocktailOverview(
    cocktailOverviewViewModel: CocktailOverviewViewModel = CocktailOverviewViewModel(),
    onViewDetailClicked: (Cocktail) -> Unit,
) {
    val cocktailOverviewState by cocktailOverviewViewModel.uiState.collectAsState()
    var selectedFavorite by remember { mutableStateOf(false) }
    var selectedAlcohol by remember { mutableStateOf(false) }
    var selectedNonalcohol by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier.fillMaxSize().background(Color(0xFFbcd2d0)),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth().background(Color(0xFF89c7bc)),
            horizontalArrangement = Arrangement.SpaceAround,
        ) {
            FilterChip(
                selected = selectedFavorite,
                onClick = { selectedFavorite = !selectedFavorite },
                label = {
                    Text("Favorites")
                },
                colors = FilterChipDefaults.filterChipColors(
                    iconColor = Color.Black,
                    containerColor = Color(0xFFd6e4e2),
                    labelColor = Color.Black,
                    selectedContainerColor = Color(0xFF6d9f96),
                    selectedLabelColor = Color.White,
                    selectedLeadingIconColor = Color.White,
                ),
                leadingIcon =  {

                        if(selectedFavorite){
                            Icon(
                                imageVector = Icons.Filled.Done,
                                contentDescription = "Done icon",
                                modifier = Modifier.size(FilterChipDefaults.IconSize),
                            )
                        }else{
                            Icon(
                                imageVector = Icons.Filled.Add,
                                contentDescription = "Done icon",
                                modifier = Modifier.size(FilterChipDefaults.IconSize),
                            )
                        }

                    }
            )

            FilterChip(
                selected = selectedAlcohol,
                onClick = { selectedAlcohol = !selectedAlcohol },
                label = {
                    Text("Alcohol")
                },
                colors = FilterChipDefaults.filterChipColors(
                    iconColor = Color.Black,
                    containerColor = Color(0xFFd6e4e2),
                    labelColor = Color.Black,
                    selectedContainerColor = Color(0xFF6d9f96),
                    selectedLabelColor = Color.White,
                    selectedLeadingIconColor = Color.White,
                ),
                leadingIcon =  {

                    if(selectedAlcohol){
                        Icon(
                            imageVector = Icons.Filled.Done,
                            contentDescription = "Done icon",
                            modifier = Modifier.size(FilterChipDefaults.IconSize),
                        )
                    }else{
                        Icon(
                            imageVector = Icons.Filled.Add,
                            contentDescription = "Done icon",
                            modifier = Modifier.size(FilterChipDefaults.IconSize),
                        )
                    }

                }
            )

            FilterChip(
                selected = selectedNonalcohol,
                onClick = { selectedNonalcohol = !selectedNonalcohol },
                label = {
                    Text("Non alcohol")
                },
                colors = FilterChipDefaults.filterChipColors(
                    iconColor = Color.Black,
                    containerColor = Color(0xFFd6e4e2),
                    labelColor = Color.Black,
                    selectedContainerColor = Color(0xFF6d9f96),
                    selectedLabelColor = Color.White,
                    selectedLeadingIconColor = Color.White,
                ),
                leadingIcon =  {

                    if(selectedNonalcohol){
                        Icon(
                            imageVector = Icons.Filled.Done,
                            contentDescription = "Done icon",
                            modifier = Modifier.size(FilterChipDefaults.IconSize),
                        )
                    }else{
                        Icon(
                            imageVector = Icons.Filled.Add,
                            contentDescription = "Done icon",
                            modifier = Modifier.size(FilterChipDefaults.IconSize),
                        )
                    }

                }
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF89c7bc)),
            horizontalArrangement = Arrangement.SpaceAround,
        ) {
            FilterChip(
                selected = selectedNonalcohol,
                onClick = { selectedNonalcohol = !selectedNonalcohol },
                label = {
                    Text("Category")
                },
                colors = FilterChipDefaults.filterChipColors(
                    iconColor = Color.Black,
                    containerColor = Color(0xFFd6e4e2),
                    labelColor = Color.Black,
                    selectedContainerColor = Color(0xFF6d9f96),
                    selectedLabelColor = Color.White,
                    selectedLeadingIconColor = Color.White,
                ),
                leadingIcon =  {

                    if(selectedNonalcohol){
                        Icon(
                            imageVector = Icons.Filled.Done,
                            contentDescription = "Done icon",
                            modifier = Modifier.size(FilterChipDefaults.IconSize),
                        )
                    }else{
                        Icon(
                            imageVector = Icons.Filled.Add,
                            contentDescription = "Done icon",
                            modifier = Modifier.size(FilterChipDefaults.IconSize),
                        )
                    }

                }
            )
            FilterChip(
                selected = selectedNonalcohol,
                onClick = { selectedNonalcohol = !selectedNonalcohol },
                label = {
                    Text("Glass")
                },
                colors = FilterChipDefaults.filterChipColors(
                    iconColor = Color.Black,
                    containerColor = Color(0xFFd6e4e2),
                    labelColor = Color.Black,
                    selectedContainerColor = Color(0xFF6d9f96),
                    selectedLabelColor = Color.White,
                    selectedLeadingIconColor = Color.White,
                ),
                leadingIcon =  {

                    if(selectedNonalcohol){
                        Icon(
                            imageVector = Icons.Filled.Done,
                            contentDescription = "Done icon",
                            modifier = Modifier.size(FilterChipDefaults.IconSize),
                        )
                    }else{
                        Icon(
                            imageVector = Icons.Filled.Add,
                            contentDescription = "Done icon",
                            modifier = Modifier.size(FilterChipDefaults.IconSize),
                        )
                    }

                }
            )
        }

        Divider(Modifier, 2.dp, Color.White)
        Cocktails(
            cocktails = cocktailOverviewState.currentCocktailList!!,
            onViewDetailClicked = onViewDetailClicked,
        )
    }
}
