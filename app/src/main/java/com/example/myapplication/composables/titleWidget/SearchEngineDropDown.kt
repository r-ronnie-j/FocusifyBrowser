package com.example.myapplication.composables.titleWidget

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.myapplication.R
import com.example.myapplication.utilities.enums.SearchEngines
import com.example.myapplication.viewModel.LocalTitleBar

@Composable
fun SearchEngineDropDown() {
    val titleBarModel = LocalTitleBar.current
    var expanded by remember { mutableStateOf(false) }

    val searchEngineResourceId = arrayOf(
        R.drawable.google,
        R.drawable.duckduckgo,
        R.drawable.yandex,
        R.drawable.bing
    )

    IconButton(onClick = { expanded = true }, modifier = Modifier.wrapContentHeight()) {
        Image(
            painter = painterResource(id = searchEngineResourceId[titleBarModel.searchEngine.ordinal]),
            contentDescription = "Show Search Engines",
            modifier = Modifier.size(
                if (
                    titleBarModel.searchEngine == SearchEngines.Duckduckgo) 20.dp else 14.dp
            )
        )
    }

    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { expanded = false }
    ) {
        DropdownMenuItem(
            text = { Text("Google") },
            onClick = {
                titleBarModel.searchEngine = SearchEngines.Google
                expanded = false
            },
            leadingIcon = {
                Image(
                    painter = painterResource(id = searchEngineResourceId[0]),
                    contentDescription = "Google Icon",
                    modifier = Modifier.size(14.dp) // Set the size of the icon
                )
            }
        )
        DropdownMenuItem(
            text = { Text("DuckDuckGo") },
            onClick = {
                titleBarModel.searchEngine = SearchEngines.Duckduckgo
                expanded = false
            },
            leadingIcon = {
                Image(
                    painter = painterResource(id = searchEngineResourceId[1]),
                    contentDescription = "DuckDuckGo Icon",
                    modifier = Modifier.size(16.dp) // Set the size of the icon
                )
            }
        )
        DropdownMenuItem(
            text = { Text("Yandex") },
            onClick = {
                titleBarModel.searchEngine = SearchEngines.Yandex
                expanded = false
            },
            leadingIcon = {
                Image(
                    painter = painterResource(id = searchEngineResourceId[2]),
                    contentDescription = "Yandex Icon",
                    modifier = Modifier.size(14.dp) // Set the size of the icon
                )
            }
        )
        DropdownMenuItem(
            text = { Text("Bing") },
            onClick = {
                titleBarModel.searchEngine = SearchEngines.Bing
                expanded = false
            },
            leadingIcon = {
                Image(
                    painter = painterResource(id = searchEngineResourceId[3]),
                    contentDescription = "Bing Icon",
                    modifier = Modifier.size(14.dp) // Set the size of the icon
                )
            }
        )
    }
}