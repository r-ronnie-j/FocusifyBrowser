package com.example.myapplication.composables

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun TopBottomDrawer() {
    var showTop by remember { mutableStateOf(false) }
    var showBottom by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxHeight()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp))
                .background(MaterialTheme.colorScheme.surface)
                .animateContentSize(
                    animationSpec = tween(400)
                )
                .height(if (showTop) 152.dp else 0.dp)
                .align(Alignment.TopStart)
                .zIndex(1f)
                .verticalScroll(rememberScrollState())
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    if (showTop || showBottom) MaterialTheme.colorScheme.surfaceContainer.copy(
                        alpha = 0.8f
                    ) else Color.Transparent
                )
                .pointerInput(Unit) {
                    detectTapGestures(
                        onTap = {
                            if (showTop || showBottom) {
                                showTop = false
                                showBottom = false
                            }
                        }
                    )
                }
        ) {
            Column {
                ElevatedButton(onClick = {
                    showTop = false
                    showBottom = !showBottom
                }) {
                    Text("Toggle Bottom Drawer")
                }
                ElevatedButton(onClick = {
                    showTop = !showTop
                    showBottom = false
                }) {
                    Text("Toggle Top Drawer")
                }
            }
            if (showTop || showBottom) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            MaterialTheme
                                .colorScheme.background.copy(alpha = 0.4f)
                        )
                ) {

                }
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                .background(MaterialTheme.colorScheme.surface)
                .animateContentSize(
                    animationSpec = tween(400)
                )
                .height(if (showBottom) 152.dp else 0.dp)
                .align(Alignment.BottomStart)
                .zIndex(1f)
                .verticalScroll(
                    rememberScrollState()
                )
        )
    }
}
