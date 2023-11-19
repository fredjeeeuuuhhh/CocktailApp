package com.example.cocktailapp.ui.cocktails.cocktaildetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun CocktailDetail(
    cocktailDetailViewModel: CocktailDetailViewModel,
    onBack: () -> Unit,
    name: String?,
) {
    val scrollState = rememberScrollState()
    val state by cocktailDetailViewModel.uiState.collectAsState()
    cocktailDetailViewModel.setCurrentCocktail(name!!)

    Column(
        modifier = Modifier.fillMaxSize().background(Color(0xFF5f9ea0)).verticalScroll(scrollState),
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.TopStart,
        ) {
            IconButton(
                onClick = {
                    onBack()
                },
                modifier = Modifier
                    .padding(end = 16.dp)
                    .height(24.dp)
                    .width(24.dp),
            ) {
                Icon(
                    Icons.Filled.ArrowBack,
                    contentDescription = "Close dialog",
                    tint = Color.White,
                    modifier = Modifier
                        .height(24.dp)
                        .width(24.dp),
                )
            }
        }
        // title
        Text(
            text = state.currentCocktail.strDrink,
            modifier = Modifier.fillMaxWidth().padding(5.dp),
            color = Color.White,
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center,
        )
        // category, glas, non/alcohol
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(5.dp),
        ) {
            Icon(
                Icons.Filled.Circle,
                "",
                tint = Color.White,
                modifier = Modifier.size(20.dp),
            )
            Text(
                text = state.currentCocktail.strCategory,
                modifier = Modifier.fillMaxWidth().padding(5.dp),
                color = Color.White,
                style = MaterialTheme.typography.titleLarge,
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(5.dp),
        ) {
            Icon(
                Icons.Filled.Circle,
                "",
                tint = Color.White,
                modifier = Modifier.size(20.dp),
            )
            Text(
                text = state.currentCocktail.strAlcoholic,
                modifier = Modifier.fillMaxWidth().padding(5.dp),
                color = Color.White,
                style = MaterialTheme.typography.titleLarge,
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(5.dp),
        ) {
            Icon(
                Icons.Filled.Circle,
                "",
                tint = Color.White,
                modifier = Modifier.size(20.dp),
            )
            Text(
                text = state.currentCocktail.strGlass,
                modifier = Modifier.fillMaxWidth().padding(5.dp),
                color = Color.White,
                style = MaterialTheme.typography.titleLarge,
            )
        }
        // ingredienten titel
        Spacer(modifier = Modifier.padding(6.dp))
        Divider(color = Color.White, thickness = 3.dp)
        Spacer(modifier = Modifier.padding(6.dp))

        Text(
            text = "Ingredients",
            modifier = Modifier.fillMaxWidth().padding(5.dp),
            color = Color.White,
            style = MaterialTheme.typography.titleLarge,
        )
        for ((i, ingredient) in state.currentCocktail.ingredients.withIndex()) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth().padding(start = 5.dp, end = 10.dp),
            ) {
                Box() {
                    Row {
                        AsyncImage(
                            model = "https://www.thecocktaildb.com/images/ingredients/$ingredient-Small.png",
                            contentDescription = ingredient,
                            modifier = Modifier
                                .width(35.dp)
                                .height(35.dp)
                                .aspectRatio(1f),
                        )
                        Text(
                            text = ingredient,
                            color = Color.White,
                            style = MaterialTheme.typography.titleLarge,
                        )
                    }
                }
                Text(
                    text = state.currentCocktail.measurements[i],
                    color = Color.White,
                    style = MaterialTheme.typography.titleLarge,
                )
            }
        }

        Spacer(modifier = Modifier.padding(6.dp))
        Divider(color = Color.White, thickness = 3.dp)
        Spacer(modifier = Modifier.padding(6.dp))

        // instructies
        Text(
            text = "Instructions",
            modifier = Modifier.fillMaxWidth().padding(5.dp),
            color = Color.White,
            style = MaterialTheme.typography.titleLarge,
        )

        for ((i, instruction) in state.currentCocktail.strInstructions.split(". ").withIndex()) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(5.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.Top,
            ) {
                Text(
                    text = (i + 1).toString(),
                    modifier = Modifier.padding(5.dp),
                    color = Color.White,
                    style = MaterialTheme.typography.titleLarge,
                )
                Text(
                    text = instruction,
                    modifier = Modifier.padding(5.dp),
                    color = Color.White,
                    style = MaterialTheme.typography.titleLarge,
                )
                /*Box(contentAlignment = Alignment.Center) {
                    Icon(
                        Icons.Filled.Circle,
                        "",
                        tint = Color.White,
                        modifier = Modifier.size(20.dp),
                    )
                    Text(
                        text = i.toString(),
                        modifier = Modifier.fillMaxWidth().padding(5.dp),
                        color = Color.Black,
                        style = MaterialTheme.typography.titleLarge,
                    )
                }*/
            }
        }
    }
}
