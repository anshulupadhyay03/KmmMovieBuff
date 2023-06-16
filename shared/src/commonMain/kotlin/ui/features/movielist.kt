@file:Suppress("INLINE_FROM_HIGHER_PLATFORM")
package ui.features

import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import data.MovieApiService
import data.PopularMoviesDataRepository
import decompose.MainScreenComponent
import domain.MovieListScreenState
import domain.MovieResult
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import io.ktor.http.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.launch
import util.getDispatcherProvider

var pageNo = 1

@Composable
fun MovieList(mainScreenComponent: MainScreenComponent) {
    val state = mainScreenComponent.viewModel.state.collectAsState()
    if(state.value is MovieListScreenState.Success){
        LazyColumn(
            modifier = Modifier
                .padding(5.dp)
                .background(Color.LightGray),
            state = rememberLazyListState().apply {
                OnBottomReached {
                    mainScreenComponent.viewModel.loadMore()
                }
            }
        ) {
            val results = (state.value as MovieListScreenState.Success).getResults()
            items(results) {
                MovieRow(it) {
                }
            }
        }
    }

}

private fun loadData(
    repo: PopularMoviesDataRepository,
    data: SnapshotStateList<MovieResult>
) {
    CoroutineScope(Job()).launch {
        val result = repo.fetchPopularMovies(pageNo++).single()
        data.addAll(result)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieRow(item: MovieResult, onItemClick: (id: Int) -> Unit) {
    //println("title : ${item.title}")
    Box(modifier = Modifier.padding(5.dp)) {
        ElevatedCard(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp),
            onClick = { onItemClick(item.id) }
        ) {
            Row {
                val painterResource = asyncPainterResource(data = "https://image.tmdb.org/t/p/original${item.imageUrl}")
                KamelImage(
                    modifier = Modifier
                        .width(100.dp)
                        .height(100.dp),
                    resource = painterResource,
                    contentScale = ContentScale.Crop,
                    contentDescription = "movieImages",
                    animationSpec = tween()
                )
                Column(
                    modifier = Modifier
                        .padding(5.dp)
                        .fillMaxHeight()
                ) {
                    Text(
                        text = item.title,
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Row {
                        Column {
                            Text(text = "Releasing On:${item.releaseDate}")
                            Text(text = "Popularity:${item.popularity}")
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxHeight()
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            AddVoteProgressBar(item.voteAverage)
                        }
                    }
                }
            }
        }
    }
}
