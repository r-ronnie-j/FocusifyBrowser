package com.example.myapplication.composables.bottomBar

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Add
import androidx.compose.material.icons.sharp.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
import androidx.compose.ui.unit.dp
import compose.icons.Octicons
import compose.icons.octicons.Globe24

@Preview(
    showSystemUi = true, showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL,
    wallpaper = Wallpapers.YELLOW_DOMINATED_EXAMPLE
)
@Composable
fun TabModal() {
    Column(
        modifier = Modifier
            .heightIn(0.dp, 600.dp)
            .padding(top = 16.dp)
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
        ) {
            IndividualTab()
            IndividualTab()
            IndividualTab()
            IndividualTab()
            IndividualTab()
            IndividualTab()
            IndividualTab()
            IndividualTab()
            IndividualTab()
            IndividualTab()
            IndividualTab()
            IndividualTab()
        }
        Box(
            modifier = Modifier
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            IconButton(onClick = {

            }) {
                Icon(imageVector = Icons.Sharp.Add, contentDescription = "Add new Tab")
            }
        }
    }
}

@Composable
fun IndividualTab() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 10.dp)
    ) {
        Icon(
            imageVector = Octicons.Globe24,
            contentDescription = "Home Page",
            modifier = Modifier.padding(start = 12.dp, end = 8.dp).height(16.dp),
        )
        Text(
            text = "AEW Search or go to new browser and find new pages and what the fuck",
            maxLines = 1,
            style = MaterialTheme.typography.bodySmall,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 10.dp)
        )
        Icon(
            imageVector = Icons.Sharp.Clear,
            contentDescription = "Home Page",
            modifier = Modifier.padding(end = 12.dp,start=8.dp).height(16.dp)
        )
    }
}

