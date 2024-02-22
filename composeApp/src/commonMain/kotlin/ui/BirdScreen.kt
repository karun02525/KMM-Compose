package ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import model.BirdImage
import viewmodel.ArticlesViewModel

@Composable
fun BirdImage(viewModel: ArticlesViewModel) {

    val uiState by viewModel.uiState.collectAsState()

    AnimatedVisibility(uiState.images.isNotEmpty()) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(5.dp),
            verticalArrangement = Arrangement.spacedBy(5.dp),
            modifier = Modifier.fillMaxSize()
                .padding(horizontal = 5.dp),
            content = {
                items(uiState.images) {
                     BirdImageCell(it)
                }
            }
        )
    }
}

@Composable
fun BirdImageCell(image: BirdImage) {
    KamelImage (
        asyncPainterResource ("https://sebastianaigner.github.io/demo-image-api/${image.path}"),
     "${image.category} by ${image.author}",
    contentScale = ContentScale.Crop,
    modifier = Modifier.fillMaxWidth().aspectRatio(1.0f))

}
