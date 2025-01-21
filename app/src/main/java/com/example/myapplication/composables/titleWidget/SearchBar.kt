package com.example.myapplication.composables.titleWidget

import android.content.res.Configuration
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
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
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Wallpapers
import com.example.myapplication.viewModel.LocalTitleBar
import com.example.myapplication.viewModel.LocalWebTabViewModel

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL,
    wallpaper = Wallpapers.GREEN_DOMINATED_EXAMPLE,
    showSystemUi = true,
    showBackground = true,
)
@Composable
fun SearchBar() {
    val titleBarViewModel = LocalTitleBar.current
    val searchText = titleBarViewModel.searchText.collectAsState()
    val webViewModel = LocalWebTabViewModel.current
    if (titleBarViewModel.isTitleBar) {
        TitleBar()
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
                value = searchText.value,
                onValueChange = { titleBarViewModel.searchText.value = it },
                keyboardActions = KeyboardActions(
                    onSearch = {
                        webViewModel.performSearch(
                            titleBarViewModel.searchText.value.text,
                            titleBarViewModel.searchEngine
                        )
                    }
                ),
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
                    if (searchText.value.text.isEmpty()) {
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
            if (searchText.value.text.isNotEmpty()) {
                IconButton(
                    onClick = { titleBarViewModel.searchText.value = TextFieldValue(text = "") },
                ) {
                    Icon(
                        imageVector = Icons.Filled.Clear,
                        contentDescription = "Clear Text",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            if (titleBarViewModel.isFocused || searchText.value.text.isNotEmpty()) {
                IconButton(onClick = {
                    webViewModel.performSearch(
                        titleBarViewModel.searchText.value.text,
                        titleBarViewModel.searchEngine
                    )
                }) {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = "Search Icon",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Spacer(modifier = Modifier.width(4.dp))
            }
        }
    }

    BackHandler {
        if (!titleBarViewModel.isTitleBar) {
            titleBarViewModel.isTitleBar = true
        }
    }

    LaunchedEffect(key1 = titleBarViewModel.isTitleBar) {
        if (!titleBarViewModel.isTitleBar) {
            titleBarViewModel.titleBarRequester.requestFocus()
        }
    }
}



