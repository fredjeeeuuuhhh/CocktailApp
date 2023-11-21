package com.example.cocktailapp.ui.cocktails.cocktailoverview.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.cocktailapp.model.ChipsModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChipSection(
    onFilterRequest: (List<String>)->Unit
){
    val filterList = listOf(
        ChipsModel(
            name= "Favorites",
        ),
        ChipsModel(
            name= "Category",
            subList = listOf("All","Ordinary Drink","Cocktail","Shake","Other / Unknown","Cocoa","Shot","Coffee / Tea","Homemade Liqueur","Punch / Party Drink","Beer","Soft Drink"),
        ),
        ChipsModel(
            name= "Glass",
            subList = listOf("All","Cocktail glass","Whiskey Glass","White wine glass","Coffee mug","Shot glass","Copper Mug","Wine Glass","Margarita/Coupette glass","Margarita glass","Martini Glass"),
        ),
        ChipsModel(
            name= "Alcoholic Filter",
            subList = listOf("All","Alcoholic","Non alcoholic","Optional alcohol"),
        ),
    )

    val selectedItems = remember { mutableStateListOf<String>() }
    var isSelected by rememberSaveable{ mutableStateOf(false) }

    LazyRow(
        modifier = Modifier
            .padding(horizontal = 5.dp)
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primaryContainer),
        userScrollEnabled = true,

        ) {
        items(filterList){chipModel->
            isSelected = selectedItems.contains(chipModel.name)
            if(chipModel.subList !=null){
                ChipWithSubItems(
                    chipLabel = chipModel.name,
                    chipItems = chipModel.subList,
                    removeFromSelectedItems = {filter -> selectedItems.remove(filter)},
                    addToSelectedItems = {filter -> selectedItems.add(filter)},
                    onFilterRequest = {onFilterRequest(selectedItems)},

                )
            }else{
                FilterChip(
                    modifier= Modifier.padding(end = 5.dp),
                    selected = isSelected,
                    onClick = {
                         when(selectedItems.contains(chipModel.name)){
                             true -> {
                                 selectedItems.remove(chipModel.name)
                                 onFilterRequest(selectedItems)
                             }
                             false -> {
                                 selectedItems.add(chipModel.name)
                                 onFilterRequest(selectedItems)
                             }
                         }
                    },
                    label = {
                        Text(chipModel.name)
                    },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = MaterialTheme.colorScheme.primary,
                        selectedLabelColor = MaterialTheme.colorScheme.onPrimary,
                        selectedLeadingIconColor = MaterialTheme.colorScheme.onPrimary,
                    ),
                    leadingIcon =  {
                        if(isSelected){
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
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChipWithSubItems(
    chipLabel:String,
    chipItems:List<String>,
    removeFromSelectedItems: (String)->Unit,
    addToSelectedItems: (String) ->Unit,
    onFilterRequest: ()->Unit
){
    var isSelected by rememberSaveable { mutableStateOf(false) }
    var showSublist by remember { mutableStateOf(false) }
    var filterName by rememberSaveable { mutableStateOf("") }
    ExposedDropdownMenuBox(
        expanded =  showSublist,
        onExpandedChange = {showSublist = !showSublist}
    ) {
        FilterChip(
            modifier = Modifier
                .menuAnchor()
                .padding(end = 5.dp),
            selected = isSelected,
            onClick = {
                isSelected = !(filterName=="All" && isSelected)
                if(!isSelected){
                    removeFromSelectedItems(filterName)
                    filterName=chipLabel
                }
            },
            label = {Text(filterName.ifEmpty { chipLabel }) },
            colors = FilterChipDefaults.filterChipColors(
                selectedContainerColor = MaterialTheme.colorScheme.primary,
                selectedLabelColor = MaterialTheme.colorScheme.onPrimary,
                selectedLeadingIconColor = MaterialTheme.colorScheme.onPrimary,
            ),
            trailingIcon = {
                Icon(
                    modifier = Modifier.rotate(if (showSublist) 180f else 0f),
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "List"
                )
            }
        )
        ExposedDropdownMenu(
            expanded = showSublist,
            onDismissRequest = {showSublist = false },
            modifier = Modifier.width(160.dp),
        ) {
            chipItems.forEach{ subItem->
                TextButton(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        removeFromSelectedItems(filterName)
                        filterName = subItem
                        showSublist = false
                        addToSelectedItems(filterName)
                        onFilterRequest()
                    },
                    colors = ButtonDefaults.textButtonColors(
                        containerColor = if (subItem==filterName || subItem == chipLabel){
                            MaterialTheme.colorScheme.primaryContainer
                        }else{
                            Color.Transparent
                        }
                    ),
                ) {
                    Text(text = subItem,color = Color.Black, textAlign = TextAlign.Center)
                }
            }
        }

    }
}