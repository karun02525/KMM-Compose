package viewmodel

import androidx.compose.runtime.mutableStateOf
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import model.Article
import model.ArticlesState
import model.BirdImage
import model.ModelBird

data class BirdState(
    val images:List<BirdImage> = emptyList()
)

class ArticlesViewModel : ViewModel() {

    private val _articlesState: MutableStateFlow<ArticlesState> = MutableStateFlow(ArticlesState(loading = true))
    val articlesState: StateFlow<ArticlesState> get() = _articlesState

    private val _uiState = MutableStateFlow<BirdState>(BirdState())
    val uiState = _uiState.asStateFlow()





    init {
        getArticles()
        updateImages()
    }

    fun getArticles(){
       viewModelScope.launch {
            delay(1000)
            val fetchArticles=fetchArticles()
            delay(1000)
            _articlesState.emit(ArticlesState(articles = fetchArticles))
        }
    }


    private val httpClient = HttpClient {
        install(ContentNegotiation){
            json()
        }
    }

    private fun updateImages(){
        viewModelScope.launch {
            val images = getImages()
            _uiState.update {
                it.copy(images=images)
            }
        }
    }


    private suspend fun getImages(): List<BirdImage> {
        return httpClient
            .get("https://sebastianaigner.github.io/demo-image-api/pictures.json")
            .body<List<BirdImage>>()
    }


    override fun onCleared() {
        super.onCleared()
        httpClient.close()
    }


    private fun fetchArticles(): List<Article> = mockArticles

    private val mockArticles = listOf(
        Article(
            "Stock market today: Live updates - CNBC",
            "Futures were higher in premarket trading as Wall Street tried to regain its footing.",
            "2023-11-09",
            "https://image.cnbcfm.com/api/v1/image/107326078-1698758530118-gettyimages-1765623456-wall26362_igj6ehhp.jpeg?v=1698758587&w=1920&h=1080"
        ),
        Article(
            "Best iPhone Deals (2023): Carrier Deals, Unlocked iPhones",
            "Apple's smartphones rarely go on sale, but if you’re looking to upgrade (or you're gift shopping), here are a few cost-saving options.",
            "2023-11-09",
            "https://media.wired.com/photos/622aa5c8cca6acf55fb70b57/191:100/w_1280,c_limit/iPhone-13-Pro-Colors-SOURCE-Apple-Gear.jpg",
        ),
        Article(
            "Samsung details ‘Galaxy AI’ and a feature that can translate phone calls in real time",
            "In a new blog post, Samsung previewed what it calls “a new era of Galaxy AI” coming to its smartphones and detailed a feature that will use artificial intelligence to translate phone calls in real time.",
            "2023-11-09",
            "https://cdn.vox-cdn.com/thumbor/Ocz_QcxUdtaexp1pPTMygaqzbR8=/0x0:2000x1333/1200x628/filters:focal(1000x667:1001x668)/cdn.vox-cdn.com/uploads/chorus_asset/file/24396795/DSC04128_processed.jpg",
        ),
    )
}