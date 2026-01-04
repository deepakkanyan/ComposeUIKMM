package org.white.green.designSystem.ui.global

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil3.compose.SubcomposeAsyncImage

/**
 * A reusable composable for displaying a rounded async image with shadow, border, dynamic properties, and click handling.
 *
 * @param imageUrl The URL of the image to load.
 * @param contentDescription Accessibility description for the image.
 * @param size The size of the image (width and height).
 * @param imageLoader Coil ImageLoader instance for loading the image.
 * @param cornerRadius Corner radius for the rounded shape.
 * @param elevation Elevation for the shadow effect.
 * @param borderWidth Width of the border (stroke).
 * @param borderColor Color of the border (stroke).
 * @param onClick Optional click callback.
 * @param onError Optional callback for handling errors with the error result.
 */
@Composable
fun RoundedAsyncImage(
    imageUrl: String,
    size: Dp,
    contentDescription: String = "",
    cornerRadius: Dp = 8.dp,
    elevation: Dp = 4.dp,
    borderWidth: Dp = 2.dp,
    borderColor: Color = MaterialTheme.colorScheme.onError,
    onClick: (() -> Unit)? = null,
    onError: ((coil3.compose.AsyncImagePainter.State.Error) -> Unit)? = null,
) {
    Box(
        modifier = Modifier
            .size(size)
            .shadow(elevation = elevation, shape = RoundedCornerShape(cornerRadius))
            .background(MaterialTheme.colorScheme.error, shape = RoundedCornerShape(cornerRadius))
            .border(width = borderWidth, color = borderColor, shape = RoundedCornerShape(cornerRadius))
            .clip(RoundedCornerShape(cornerRadius))
            .then(if (onClick != null) Modifier.clickable { onClick() } else Modifier),
        contentAlignment = Alignment.Center,
    ) {
        SubcomposeAsyncImage(
            model = imageUrl,
            contentDescription = contentDescription,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.size(size),
            loading = {
                Text(
                    text = "Loading",
                    color = MaterialTheme.colorScheme.onError,
                    style = MaterialTheme.typography.bodyMedium,
                )
            },
            error = {
                Text(
                    text = "Error",
                    color = MaterialTheme.colorScheme.onError,
                    style = MaterialTheme.typography.bodyMedium,
                )
            },
            onError = { error ->
                onError?.invoke(error)
                println("Deepal ${error.result}")
            },
        )
    }
}