package com.example.myapplication.composables.widgets

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import compose.icons.Octicons
import compose.icons.octicons.ChevronLeft24

@Composable
fun TopBar(
    onClick: (() -> Unit)?,
    text: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(
                top = 4.dp,
                bottom = 5.dp,
                start = 8.dp,
                end = 8.dp
            )
            .fillMaxWidth(),
    ) {
        if (onClick != null) {
            IconButton(onClick = onClick) {
                Icon(imageVector = Octicons.ChevronLeft24, contentDescription = "Go Back")
            }
        }
        Text(
            text = text,
            style = MaterialTheme.typography.headlineLarge,
            fontSize = 18.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}