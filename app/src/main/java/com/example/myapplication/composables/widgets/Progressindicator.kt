package com.example.myapplication.composables.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp

@Composable
fun ProgressIndicator(progress: Float) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(4.dp)  // Adjust thickness
            .background(Color.Gray.copy(alpha = 0.3f), shape = RectangleShape) // Background
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(progress)  // Fill according to progress
                .height(4.dp)
                .background(
                    MaterialTheme.colorScheme.primary,
                    shape = RectangleShape
                ) // Progress bar color
        )
    }
}

