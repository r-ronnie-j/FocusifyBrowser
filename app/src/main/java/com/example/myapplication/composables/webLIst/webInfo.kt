package com.example.myapplication.composables.webLIst

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import compose.icons.Octicons
import compose.icons.octicons.Globe24

@Composable
fun WebInfo(
    favIcon: Bitmap?,
    title: String?,
    url: String?
) {
    return Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 8.dp, horizontal = 8.dp)
    ) {
        Box {
            if (favIcon == null) {
                Icon(
                    imageVector = Octicons.Globe24,
                    contentDescription = "Home Page",
                    modifier = Modifier
                        .size(20.dp),
                )
            } else {
                Image(
                    painter = BitmapPainter(favIcon.asImageBitmap()),
                    contentDescription = title,
                    modifier = Modifier
                        .size(20.dp),
                )
            }
        }
        Spacer(modifier = Modifier.width(10.dp))
        Column {
            if (title != null) {
                Text(
                    text = title,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.titleMedium
                )
            }
            if (url != null) {
                Text(
                    text = url,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.labelMedium
                )
            }

        }
    }
}