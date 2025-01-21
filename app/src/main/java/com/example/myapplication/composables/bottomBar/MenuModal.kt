import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import com.composables.icons.lucide.Bookmark
import com.composables.icons.lucide.BookmarkPlus
import com.composables.icons.lucide.Download
import com.composables.icons.lucide.Filter
import com.composables.icons.lucide.History
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.Printer
import com.composables.icons.lucide.Share
import compose.icons.Octicons
import compose.icons.octicons.Pulse16
import compose.icons.octicons.DeviceDesktop24

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun MenuModal() {
    val menuItems = listOf(
        MenuItem("History", Lucide.History),
        MenuItem("Bookmarks", Lucide.Bookmark),
        MenuItem("Incognito", Octicons.Pulse16),
        MenuItem("Add Bookmark", Lucide.BookmarkPlus),
        MenuItem("Downloads", Lucide.Download),
        MenuItem("Print Page", Lucide.Printer),
        MenuItem("Share", Lucide.Share),
        MenuItem("Desktop Site", Octicons.DeviceDesktop24),
        MenuItem("Filter", Lucide.Filter)
    )

    LazyVerticalGrid(
        columns = GridCells.Fixed(5),
        modifier = Modifier
            .heightIn(0.dp, 400.dp)
            .padding(top = 20.dp)

    ) {
        items(menuItems.size) { index ->
            MenuItemView(item = menuItems[index], onClick = { /* Handle click */ })
        }
    }
}

@Composable
fun MenuItemView(item: MenuItem, onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(vertical = 8.dp)
            .clickable { onClick() }
            .fillMaxWidth()
    ) {
        Icon(
            imageVector = item.icon,
            contentDescription = item.label,
            modifier = Modifier.size(16.dp),

        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = item.label,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.labelSmall
        )
    }
}

data class MenuItem(val label: String, val icon: ImageVector)
