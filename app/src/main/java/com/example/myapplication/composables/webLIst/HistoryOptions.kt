package com.example.myapplication.composables.webLIst

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.myapplication.viewModel.LocalHistoryViewModel
import com.example.myapplication.viewModel.LocalWebTabViewModel

@Composable
fun HistoryOptions() {
    val webviewModel = LocalWebTabViewModel.current
    val historyModel = LocalHistoryViewModel.current

    Column {
        Spacer(modifier = Modifier.height(3.dp))
        HorizontalDivider()
        Spacer(modifier = Modifier.height(3.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 12.dp)
        ) {
            Text(
                text = "Edit",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable { historyModel.isEdit = !historyModel.isEdit }
            )
            Spacer(modifier = Modifier.width(30.dp))
            Text(
                text = if (historyModel.isEdit) "Delete" else "Delete All",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable {
                    if (historyModel.isEdit) {
                        historyModel.deleteHistory.forEach {
                            webviewModel.deleteHistoryById(it)
                        }
                    } else {
                        webviewModel.clearHistory()
                    }
                }
            )
        }
    }

}