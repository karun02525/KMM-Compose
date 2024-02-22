package ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import org.jetbrains.compose.resources.ExperimentalResourceApi
import viewmodel.ArticlesViewModel
import kotlin.math.absoluteValue

@OptIn(ExperimentalResourceApi::class)
@Composable
fun App() {
    MaterialTheme {
        var showContent by remember { mutableStateOf(false) }

        val viewModel = getViewModel(Unit, viewModelFactory {ArticlesViewModel()})

        LaunchedEffect(Unit){
           // println(getImages())
        }

        //ArticleScreen(viewModel)
        BirdImage(viewModel)

        /*Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {


            Button(onClick = { showContent = !showContent }) {
                Text("Click me!")
            }


            AnimatedVisibility(showContent) {
                Column(Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally) {
                    KamelImage(
                        asyncPainterResource("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR9ct8N94Cpdkm00muTMyQgedNJB5T8GcIACfYInnhmVg&s"),null)


                   // Image(painterResource("logo.png"), null)
                }
            }
        }*/
    }
}

/*
val httpClient = HttpClient{
    install(ContentNegotiation){
        json()
    }
}

suspend fun getImages(): List<BirdImage> {
   // return httpClient.get("https://sebi.io/demo-image-api/pictures.json")
    return httpClient
        .get("https://sebastianaigner.github.io/demo-image-api/pictures.json")
        .body<List<BirdImage>>()
}

*/



@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PagerExample() {
    val linkList = listOf(
        "https://images.hepsiburada.net/banners/s/0/896-430/FLEX_kosullu_15ocak_2133497731472971918.jpg/format:webp",
        "https://images.hepsiburada.net/banners/s/1/960-480/163994_Flex133525667113621419.jpg/format:webp",
        "https://images.hepsiburada.net/banners/s/1/960-480/960x480_copy_2133528868004381685.jpg/format:webp",
        "https://images.hepsiburada.net/banners/s/1/960-480/960x480_copy_2133529123069712259133529133075581734.png/format:webp",
    )
    val pagerState = rememberPagerState(pageCount = { linkList.size })

    HorizontalPager(state = pagerState) { page ->
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .graphicsLayer {
                    // Calculate the absolute offset for the current page from the
                    // scroll position. We use the absolute value which allows us to mirror
                    // any effects for both directions
                    val pageOffset = (
                            (pagerState.currentPage - page) + pagerState
                                .currentPageOffsetFraction
                            ).absoluteValue

                    // We animate the alpha, between 10% and 100%
                    alpha = lerp(
                        start = 0.1f,
                        stop = 1f,
                        fraction = 1f - pageOffset.coerceIn(0f, 1f)
                    )
                    // Translate the box horizontally based on the page offset
                    translationX = pageOffset * 200 // adjust the value as needed

                    // Scale the box based on the page offset
                    scaleX = 1f - pageOffset * 0.2f // adjust the value as needed
                    scaleY = 1f - pageOffset * 0.2f // adjust the value as needed

                }
                .padding(16.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(Color.LightGray)
        )
        {
            KamelImage(
                asyncPainterResource(linkList.getOrNull(page)?:""),null)
           /* AsyncImage(
                modifier = Modifier.fillMaxSize(),
                model = linkList.getOrNull(page),
                contentScale = ContentScale.FillBounds,
                contentDescription = ""
            )*/

        }
    }
}