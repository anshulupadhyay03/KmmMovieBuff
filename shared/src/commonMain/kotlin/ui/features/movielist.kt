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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import decompose.MainScreenComponent
import domain.ListState
import domain.MovieResult
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import io.ktor.http.*
import util.OnBottomReached

@Composable
fun MovieList(mainScreenComponent: MainScreenComponent) {
    val movieList = mainScreenComponent.viewModel.movieLists
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

        items(movieList, key = {it.id}) {
            MovieRow(it) {movieId ->
                mainScreenComponent.viewModel.onItemClicked(movieId)
            }
        }

        item(
            key = mainScreenComponent.viewModel.listState
        ){
            when(mainScreenComponent.viewModel.listState){
                ListState.LOADING ->{
                    LoadingItem()
                }

                ListState.PAGINATING -> {
                    LoadingItem()
                }
                else ->{
                    
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieRow(item: MovieResult, onItemClick: (id: Int) -> Unit) {
    Box(modifier = Modifier.padding(5.dp)) {
        ElevatedCard(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp),
            onClick = { onItemClick(item.id) }
        ) {
            Row {
                val painterResource =
                    asyncPainterResource(data = "https://image.tmdb.org/t/p/original${item.imageUrl}")
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
