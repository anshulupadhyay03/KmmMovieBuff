package ui.features

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.seiko.imageloader.rememberAsyncImagePainter
import decompose.MainScreenComponent
import domain.ListState
import domain.MovieResult
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
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
        items(movieList, key = { it.id }) {
            MovieRow(it) { movieId ->
                mainScreenComponent.viewModel.onItemClicked(movieId)
            }
        }
        item(
            key = mainScreenComponent.viewModel.listState
        ) {
            when (mainScreenComponent.viewModel.listState) {
                ListState.LOADING -> {
                    LoadingItem()
                }

                ListState.PAGINATING -> {
                    LinearProgressIndicator(
                        modifier = Modifier.padding(top = 5.dp, bottom = 5.dp).fillMaxWidth()
                    )
                }
                else -> {

                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalResourceApi::class)
@Composable
fun MovieRow(item: MovieResult, onItemClick: (id: Int) -> Unit) {
    Box(modifier = Modifier.padding(5.dp)) {
        ElevatedCard(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp),
            onClick = { onItemClick(item.id) }
        ) {
            Row {

                ShowMovieImage(item.imageUrl, item.voteAverage)

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
                            Row(horizontalArrangement = Arrangement.Center) {
                                Icon(painter = painterResource("date_range.xml"), "Release date")
                                Text(text = item.releaseDate)
                            }

                            Text(
                                text = item.overview,
                                maxLines = 3,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                        /*Row(
                            modifier = Modifier
                                .fillMaxHeight()
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                        }*/
                    }
                }
            }
        }
    }
}


@OptIn(ExperimentalTextApi::class)
@Composable
private fun ShowMovieImage(path: String, voteAverage: Double) {
    val painterResource =
        rememberAsyncImagePainter("https://image.tmdb.org/t/p/original$path")
    val ratingBarColors = getRatingBarColors(voteAverage)
    val measurer = rememberTextMeasurer()
    Image(
        painter = painterResource,
        modifier = Modifier
            .width(120.dp)
            .height(150.dp)
            .aspectRatio(1f)
            .padding(3.dp)
            .graphicsLayer {
                compositingStrategy = CompositingStrategy.Offscreen
            }
            .drawWithCache {
                val path = Path()
                path.addOval(
                    Rect(
                        topLeft = Offset.Zero,
                        bottomRight = Offset(size.width, size.height)
                    )
                )
                onDrawWithContent {
                    clipPath(path) {
                        this@onDrawWithContent.drawContent()
                    }
                    val dotSize = size.width / 8f

                    drawCircle(
                        Color.Black,
                        radius = dotSize,
                        center = Offset(
                            x = size.width - dotSize,
                            y = size.height - dotSize
                        ),
                        blendMode = BlendMode.Color
                    )
                    drawCircle(
                        ratingBarColors.highlightColor, radius = dotSize * 0.8f,
                        center = Offset(
                            x = size.width - dotSize,
                            y = size.height - dotSize
                        )
                    )
                    drawText(
                        textMeasurer = measurer,
                        text = "${(voteAverage * 10).toInt()}%",
                        style = TextStyle(
                            color = Color.White ,
                            fontSize = 10.sp
                        ),
                        topLeft = Offset(
                            x = size.width - dotSize - 20,
                            y = size.height - dotSize - 20
                        )
                    )
                }
            },
        contentScale = ContentScale.Crop,
        contentDescription = ""
    )
}


