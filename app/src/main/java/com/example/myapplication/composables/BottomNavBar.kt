package com.example.myapplication.composables

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import compose.icons.Octicons
import compose.icons.octicons.ChevronLeft24
import compose.icons.octicons.ChevronRight24
import compose.icons.octicons.Home24
import compose.icons.octicons.Share24
import compose.icons.octicons.Square24
import compose.icons.octicons.ThreeBars16

@OptIn(ExperimentalMaterial3Api::class)
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL,
    wallpaper = Wallpapers.GREEN_DOMINATED_EXAMPLE,
    showSystemUi = true,
    showBackground = true,
)
@Composable
fun BottomNavBar() {
    var showModal by remember { mutableStateOf(false) }
    var modalContent by remember { mutableStateOf("") }


    if (showModal) {
        ModalBottomSheet(onDismissRequest = { showModal = false }) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .background(MaterialTheme.colorScheme.surfaceContainer)
            ) {
                Text(
                    text = "Bottom Modal: $modalContent",
                    fontSize = 16.sp,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Button(
                    onClick = { showModal = false },
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Text("Close")
                }
            }
        }
    }

    BottomAppBar(
        modifier = Modifier
            .fillMaxWidth()
            .height(45.dp),
        containerColor = Color.Transparent
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            IconButton(onClick = { /* Handle back navigation */ }) {
                Icon(
                    imageVector = Octicons.ChevronLeft24,
                    contentDescription = "Backward",
                    modifier = Modifier.size(32.dp),
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }
            IconButton(onClick = { /* Handle forward navigation */ }) {
                Icon(
                    imageVector = Octicons.ChevronRight24,
                    contentDescription = "Forward",
                    modifier = Modifier.size(32.dp),
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }
            IconButton(onClick = { /* Handle home navigation */ }) {
                Icon(
                    imageVector = Octicons.Home24,
                    contentDescription = "Home",
                    modifier = Modifier.size(24.dp),
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }
            IconButton(onClick = {
                modalContent = "Tabs"
                showModal = true
            }) {
                Icon(
                    imageVector = Octicons.Square24,
                    contentDescription = "Tabs",
                    modifier = Modifier.size(36.dp),
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }
            IconButton(onClick = {
                modalContent = "Menu"
                showModal = true
            }) {
                Icon(
                    imageVector = Octicons.ThreeBars16,
                    contentDescription = "Menu",
                    modifier = Modifier.size(20.dp),
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }
        }
    }

}


