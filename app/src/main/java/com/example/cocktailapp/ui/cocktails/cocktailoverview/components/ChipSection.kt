package com.example.cocktailapp.ui.cocktails.cocktailoverview.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.cocktailapp.R

@Composable
fun ChipSection(
    onFilterRequest: (List<String>)->Unit
){
    val filterList = listOf(
        stringResource(R.string.cat_ordinary_drink),
        stringResource(R.string.cat_cocktail),
        stringResource(R.string.cat_shake),
        stringResource(R.string.cat_other),
        stringResource(R.string.cat_cocoa),
        stringResource(R.string.cat_shot),
        stringResource(R.string.cat_coffee_tea),
        stringResource(R.string.cat_liqueur),
        stringResource(R.string.cat_punch),
        stringResource(R.string.cat_beer),
        stringResource(R.string.cat_soft_drink)
    )
    val selectedItems = remember { mutableStateListOf<String>() }
    LazyRow(
        modifier = Modifier
            .padding(horizontal = 5.dp)
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primaryContainer),
        userScrollEnabled = true,

        ) {
        items(filterList) { category ->
            CategoryFilterChip(
                category = category,
                onChipClicked = {
                    item ->
                    if(selectedItems.contains(item)){
                        selectedItems.remove(item)
                    }else{
                        selectedItems.add(item)
                    }
                    onFilterRequest(selectedItems)
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryFilterChip(
    category:String,
    onChipClicked:(String)->Unit,
){
    var isSelected by rememberSaveable{ mutableStateOf(false) }
    FilterChip(
        modifier = Modifier.padding(end = 5.dp),
        selected = isSelected,
        onClick = {
            isSelected= !isSelected
            onChipClicked(category)
        },
        label = {
            Text(category)
        },
        colors = FilterChipDefaults.filterChipColors(
            selectedContainerColor = MaterialTheme.colorScheme.primary,
            selectedLabelColor = MaterialTheme.colorScheme.onPrimary,
            selectedLeadingIconColor = MaterialTheme.colorScheme.onPrimary,
        ),
        leadingIcon = {
            if (isSelected) {
                Icon(
                    imageVector = Icons.Filled.Done,
                    contentDescription = "Done icon",
                    modifier = Modifier.size(FilterChipDefaults.IconSize),
                )
            }
        }
    )
}