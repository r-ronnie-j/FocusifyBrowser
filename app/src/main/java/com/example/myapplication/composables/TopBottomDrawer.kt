package com.example.myapplication.composables

import MenuModal
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.myapplication.composables.bottomBar.TabModal
import com.example.myapplication.composables.titleWidget.SuggestionComposable
import com.example.myapplication.viewModel.LocalTitleBar

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun TopBottomDrawer() {

    var showTop by remember { mutableStateOf(false) }
    var showBottom by remember { mutableStateOf(false) }
    val titleBarViewModel = LocalTitleBar.current
    val suggestions = titleBarViewModel.suggestions.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxHeight()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp))
                .background(MaterialTheme.colorScheme.surface)
                .animateContentSize(animationSpec = tween(400))
                .heightIn(min = 0.dp, max = 250.dp)
                .align(Alignment.TopStart)
                .zIndex(1f)
                .verticalScroll(rememberScrollState())
        ) {
            if (!titleBarViewModel.isTitleBar && suggestions.value.isNotEmpty()) {
                Column {
                    suggestions.value.forEach {
                        SuggestionComposable(suggestion = it)
                    }
                }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    if (showTop || showBottom) MaterialTheme.colorScheme.surfaceContainer.copy(
                        alpha = 0.5f
                    ) else Color.Transparent
                )
                .pointerInput(Unit) {
                    detectTapGestures(
                        onTap = {
                            if (showTop || showBottom) {
                                showTop = false
                                showBottom = false
                            }
                            titleBarViewModel.isTitleBar = true
                        }
                    )
                }
        ) {
            WebViewComposable()
            AnimatedAlphaBox(showTop, showBottom)
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                .background(MaterialTheme.colorScheme.surface)
                .animateContentSize(
                    animationSpec = tween(200)
                )
                .align(Alignment.BottomStart)
                .zIndex(1f)
                .verticalScroll(
                    rememberScrollState()
                )
        ) {
            if (titleBarViewModel.showTabs) {
                TabModal()
            }
            if (titleBarViewModel.showMenu) {
                MenuModal()
            }
        }
    }

    LaunchedEffect(key1 = titleBarViewModel.isTitleBar) {
        showTop = !titleBarViewModel.isTitleBar
    }

    LaunchedEffect(titleBarViewModel.showTabs, titleBarViewModel.showMenu) {
        showBottom = titleBarViewModel.showTabs || titleBarViewModel.showMenu
    }

}


@Composable
fun AnimatedAlphaBox(showTop: Boolean, showBottom: Boolean) {
    val titleBarViewModel = LocalTitleBar.current
    val animatedAlpha = animateFloatAsState(
        targetValue = if (showTop || showBottom) 0.2f else 0f,
        animationSpec = tween(durationMillis = 300)
    )

    if (showTop || showBottom) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    MaterialTheme.colorScheme.onBackground.copy(alpha = animatedAlpha.value)
                )
                .pointerInput(Unit) {
                    detectTapGestures(
                        onTap = {
                            titleBarViewModel.isTitleBar = true
                            titleBarViewModel.showTabs = false
                            titleBarViewModel.showMenu = false
                        }
                    )
                }
        )
    }
}