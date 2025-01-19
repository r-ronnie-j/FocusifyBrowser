package com.example.myapplication.composables.titleWidget

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.composables.icons.lucide.ArrowUpLeft
import com.composables.icons.lucide.Clipboard
import com.composables.icons.lucide.History
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.Search
import com.example.myapplication.composables.viewModel.LocalTitleBar
import com.example.myapplication.utilData.SearchSuggestion
import com.example.myapplication.utilData.SuggestionType

@Composable
fun SuggestionComposable(suggestion: SearchSuggestion) {

    val clipboardManager = LocalClipboardManager.current
    val titleBarViewModel = LocalTitleBar.current

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp)
    ) {
        Icon(
            imageVector = if (suggestion.type == SuggestionType.History)
                Lucide.History else Lucide.Search,
            contentDescription = if (suggestion.type == SuggestionType.History) "From your history" else "Internet suggestions",
            tint = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.size(20.dp)
        )
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 8.dp)
                .clickable {
                    titleBarViewModel.searchText.value = TextFieldValue(
                        text = suggestion.text,
                        selection = TextRange(index = suggestion.text.length)
                    )
                }
        ) {
            Text(
                text = suggestion.text,
                style = MaterialTheme.typography.labelMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            suggestion.source?.let {
                Text(
                    text = suggestion.source,
                    style = MaterialTheme.typography.bodySmall,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
            }
        }
        Icon(
            imageVector = Lucide.Clipboard,
            contentDescription = "Copy to clipboard",
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .size(16.dp)
                .clickable {
                    clipboardManager.setText(AnnotatedString(suggestion.text))
                }
        )
        Icon(
            imageVector = Lucide.ArrowUpLeft,
            contentDescription = "Select and edit",
            modifier = Modifier
                .size(20.dp)
                .clickable {
                    titleBarViewModel.searchText.value = TextFieldValue(
                        text = suggestion.text,
                        selection = TextRange(index = suggestion.text.length)
                    )
                }
        )
    }
}

@Preview(
    showSystemUi = true, showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL,
    device = "spec:id=reference_phone,shape=Normal,width=411,height=891,unit=dp,dpi=420",
)
@Composable
fun SuggestionComposablePreview() {
    Column {
        SuggestionComposable(
            suggestion = SearchSuggestion(
                type = SuggestionType.History,
                text = "aew"
            )
        )
        SuggestionComposable(
            suggestion = SearchSuggestion(
                type = SuggestionType.History,
                text = "aew"
            )
        )
        SuggestionComposable(
            suggestion = SearchSuggestion(
                type = SuggestionType.History,
                text = "aew"
            )
        )
    }

}