package com.example.myapplication.composables.titleWidget

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Wallpapers
import com.example.myapplication.R
import com.example.myapplication.composables.viewModel.LocalTitleBar

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL,
    wallpaper = Wallpapers.GREEN_DOMINATED_EXAMPLE,
    showSystemUi = true,
    showBackground = true,
)
@Composable
fun SearchBar() {
    val titleBarViewModel = LocalTitleBar.current
    if (titleBarViewModel.isTitleBar) {
        TitleBar(searchTitle = "AEW Google Search")
    } else {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            SearchEngineDropDown()
            Spacer(modifier = Modifier.width(4.dp))
            BasicTextField(
                value = titleBarViewModel.searchText,
                onValueChange = { titleBarViewModel.searchText = it },
                modifier = Modifier
                    .weight(1f)
                    .background(Color.Transparent)
                    .focusRequester(titleBarViewModel.titleBarRequester)
                    .onFocusEvent {
                        titleBarViewModel.isFocused = it.isFocused
                    },
                textStyle = LocalTextStyle.current.copy(
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Start
                ),
                cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
                singleLine = true,
                decorationBox = { innerTextField ->
                    if (titleBarViewModel.searchText.isEmpty()) {
                        Text(
                            text = "  Search or type URL",
                            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                    innerTextField()
                }
            )
            Spacer(modifier = Modifier.width(4.dp))
            if (titleBarViewModel.searchText.isNotEmpty()) {
                IconButton(
                    onClick = { titleBarViewModel.searchText = "" },
                ) {
                    Icon(
                        imageVector = Icons.Filled.Clear,
                        contentDescription = "Clear Text",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            if (titleBarViewModel.isFocused || titleBarViewModel.searchText.isNotEmpty()) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "Search Icon",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.width(4.dp))
            }


        }
    }

    LaunchedEffect(key1 = titleBarViewModel.isTitleBar) {
        if (!titleBarViewModel.isTitleBar) {
            titleBarViewModel.titleBarRequester.requestFocus()
        }
    }
}


@Composable
fun SearchEngineDropDown() {
    var expanded by remember { mutableStateOf(false) }

    var searchEngine by remember {
        mutableIntStateOf(0)
    }

    val searchEngineResourceId = arrayOf(
        R.drawable.google,
        R.drawable.duckduckgo,
        R.drawable.yandex,
        R.drawable.bing
    )

    IconButton(onClick = { expanded = true }, modifier = Modifier.wrapContentHeight()) {
        Image(
            painter = painterResource(id = searchEngineResourceId[searchEngine]),
            contentDescription = "Show Search Engines",
            modifier = Modifier.size(if (searchEngine == 1) 20.dp else 14.dp)
        )
    }

    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { expanded = false }
    ) {
        DropdownMenuItem(
            text = { Text("Google") },
            onClick = {
                searchEngine = 0
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
                searchEngine = 1
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
                searchEngine = 2
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
                searchEngine = 3
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
