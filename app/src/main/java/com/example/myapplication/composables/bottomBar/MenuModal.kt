import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.composables.icons.lucide.Bookmark
import com.composables.icons.lucide.BookmarkPlus
import com.composables.icons.lucide.Download
import com.composables.icons.lucide.Filter
import com.composables.icons.lucide.History
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.Printer
import com.example.myapplication.R
import com.example.myapplication.navigation.LocalMainNavigationProvider
import com.example.myapplication.navigation.MainNavigation
import com.example.myapplication.viewModel.LocalTitleBar
import com.example.myapplication.viewModel.LocalWebTabViewModel
import compose.icons.Octicons
import compose.icons.octicons.DeviceDesktop24
import compose.icons.octicons.ShareAndroid24

sealed class IconImage {
    class ImageVectorIcon(val imageVector: ImageVector) : IconImage()
    class ImageIcon(val image: Painter) : IconImage()
}

data class MenuItem(
    val label: String,
    val icon: IconImage,
    val onClick: () -> Unit,
    val active: Boolean,
)

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun MenuModal() {

    val mainNavigator = LocalMainNavigationProvider.current
    val titleViewModel = LocalTitleBar.current
    val webViewModel = LocalWebTabViewModel.current
    val context = LocalContext.current

    val menuItems = listOf(
        MenuItem("History", IconImage.ImageVectorIcon(Lucide.History), onClick = {
            titleViewModel.showMenu = false
            mainNavigator.navigate(MainNavigation.HistoryPage.name)
        }, false),
        MenuItem(
            "Bookmarks",
            IconImage.ImageVectorIcon(Lucide.Bookmark),
            onClick = {
                titleViewModel.showMenu = false
                mainNavigator.navigate(MainNavigation.BookmarkPage.name)
            }, false
        ),
        MenuItem(
            "Incognito",
            IconImage.ImageIcon(painterResource(id = R.drawable.incognito)),
            onClick = {
                webViewModel.isIncognito = !webViewModel.isIncognito
            }, webViewModel.isIncognito
        ),
        MenuItem(
            "Add Bookmark", IconImage.ImageVectorIcon(Lucide.BookmarkPlus), onClick = {}, false
        ),
        MenuItem("Downloads", IconImage.ImageVectorIcon(Lucide.Download), onClick = {}, false),
        MenuItem("Print Page", IconImage.ImageVectorIcon(Lucide.Printer), onClick = {}, false),
        MenuItem("Share", IconImage.ImageVectorIcon(Octicons.ShareAndroid24), onClick = {
            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(
                    Intent.EXTRA_TEXT,
                    webViewModel.webViewTabs[webViewModel.activeIndex.intValue].url
                )
            }
            context.startActivity(Intent.createChooser(shareIntent, "Share via"))
        }, false),
        MenuItem(
            "Desktop Site",
            IconImage.ImageVectorIcon(Octicons.DeviceDesktop24),
            onClick = {},
            false
        ),
        MenuItem("Filter", IconImage.ImageVectorIcon(Lucide.Filter), onClick = {
            titleViewModel.showMenu = false
            mainNavigator.navigate(MainNavigation.FilterPage.name)
        }, false)
    )

    LazyVerticalGrid(
        columns = GridCells.Fixed(5),
        modifier = Modifier
            .heightIn(0.dp, 400.dp)
            .padding(top = 20.dp)

    ) {
        items(menuItems.size) { index ->
            MenuItemView(item = menuItems[index])
        }
    }
}

@Composable
fun MenuItemView(item: MenuItem) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(vertical = 8.dp)
            .clickable { item.onClick() }
            .fillMaxWidth()
    ) {
        when (item.icon) {
            is IconImage.ImageVectorIcon -> Icon(
                imageVector = item.icon.imageVector,
                contentDescription = item.label,
                modifier = Modifier.size(16.dp),
                tint = if (item.active) MaterialTheme.colorScheme.primary
                else MaterialTheme.colorScheme.onSurface
            )

            is IconImage.ImageIcon -> {
                Image(
                    painter = item.icon.image, contentDescription = item.label,
                    modifier = Modifier.size(18.dp),
                    colorFilter = ColorFilter.tint(
                        if (item.active) MaterialTheme.colorScheme.primary else
                            MaterialTheme.colorScheme.onBackground
                    )
                )
            }

            else -> {}
        }

        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = item.label,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.labelSmall,
            color = if (item.active) MaterialTheme.colorScheme.primary else
                MaterialTheme.colorScheme.onSurface
        )
    }
}

