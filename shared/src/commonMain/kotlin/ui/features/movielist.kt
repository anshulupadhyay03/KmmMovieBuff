package ui.features

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.seiko.imageloader.rememberAsyncImagePainter
import decompose.MainScreenComponent
import domain.ListState
import domain.MovieResult
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
                   LinearProgressIndicator(modifier = Modifier.padding(top = 5.dp, bottom = 5.dp).fillMaxWidth())
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

                val painterResource = rememberAsyncImagePainter("https://image.tmdb.org/t/p/original${item.imageUrl}")

                Image(
                    painter = painterResource,
                    modifier = Modifier
                        .width(100.dp)
                        .height(100.dp),
                    contentScale = ContentScale.Crop,
                    contentDescription = ""
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
