package ui.features

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.seiko.imageloader.rememberAsyncImagePainter
import decompose.DetailsScreenComponent
import domain.*
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource


@Composable
fun MovieDetailsScreen(component: DetailsScreenComponent) {
    val movieDetailState by component.viewModel.movieDetailsState.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        when (movieDetailState) {
            MovieDetailsUiState.Loading -> {
                Text("Loading")
            }
            is MovieDetailsUiState.Success -> {
                print("hi coming to success")
                ShowMovieDetails((movieDetailState as MovieDetailsUiState.Success).movieDetails)
            }
            else -> {
                Text("Error")
            }
        }
    }
}

@Composable
fun ShowCasts(casts: List<MovieCast>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
    ) {
        ShowHeaderText("Top Actors")
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .padding(top = 5.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
           items(casts){
               Column(
                   modifier = Modifier
                       .padding(start = 5.dp, end = 5.dp),
                   horizontalAlignment = Alignment.CenterHorizontally
               ) {

                   val painterResource = rememberAsyncImagePainter("https://image.tmdb.org/t/p/original${it.avatarPath}")

                   Image(
                       painter = painterResource,
                       modifier = Modifier
                           .width(80.dp)
                           .height(100.dp)
                           .clip(RoundedCornerShape(10)),
                       contentScale = ContentScale.Crop,
                       contentDescription = "reviews"
                   )

                   /* val painterResource =
                        asyncPainterResource(data = "https://image.tmdb.org/t/p/original${it.avatarPath}")
                    KamelImage(
                        modifier = Modifier
                            .width(80.dp)
                            .height(100.dp)
                            .clip(RoundedCornerShape(10)),
                        resource = painterResource,
                        contentScale = ContentScale.Crop,
                        contentDescription = "reviews",
                        animationSpec = tween()
                    )*/
                   Text(
                       modifier = Modifier
                           .fillMaxWidth()
                           .widthIn(max = 100.dp),
                       text = it.name,
                       color = Color.DarkGray,
                       fontWeight = FontWeight.Bold,
                       overflow = TextOverflow.Ellipsis
                   )
                   Text(
                       modifier = Modifier
                           .fillMaxWidth()
                           .widthIn(max = 100.dp),
                       text = it.characterName,
                       color = Color.DarkGray,
                       overflow = TextOverflow.Ellipsis
                   )
               }
           }
        }
    }

}

@Composable
private fun ShowHeaderText(title: String) {
    Text(
        modifier = Modifier
            .fillMaxWidth(),
        text = title,
        color = Color.DarkGray,
        fontWeight = FontWeight.Bold
    )
}

@Composable
fun ShowKeyWords(keywords: List<String>) {
    ChipVerticalGrid(
        spacing = 3.dp,
        modifier = Modifier
            .padding(7.dp)
    ) {
        keywords.forEach { word ->
            Text(
                fontSize = 8.sp,
                text = word,
                color = Color.White,
                modifier = Modifier
                    .background(color = MaterialTheme.colorScheme.primaryContainer, shape = CircleShape)
                    .padding(vertical = 2.dp, horizontal = 5.dp)
            )
        }
    }
}

@Composable
fun ShowMovieDetails(movieDetailsModel: MovieDetailsModel) {
    ShowImages(movieDetailsModel)
    ShowOverview(movieDetailsModel.overview, movieDetailsModel.vote)
    ShowMovieInfo(movieDetailsModel.movieInfo)
    if (movieDetailsModel.reviews.isNotEmpty()) {
        ShowReviews(movieDetailsModel.reviews)
    }
    ShowKeyWords(movieDetailsModel.keywords)
    ShowCasts(movieDetailsModel.topCast)
    ShowPostersAndBackDrops(movieDetailsModel.posters, movieDetailsModel.backdrops)
    //ShowVideos(movieDetailsModel.videos)
}

@Composable
fun ShowPostersAndBackDrops(posters: List<String>, backdrops: List<String>) {
    Column(modifier = Modifier.padding(5.dp)) {
        if (posters.isNotEmpty()) {
            ShowHeaderText("Posters")
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                horizontalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                items(posters){
                    val painterResource = rememberAsyncImagePainter("https://image.tmdb.org/t/p/original$it")

                    Image(
                        painter = painterResource,
                        modifier = Modifier
                            .width(200.dp)
                            .height(200.dp),
                        contentScale = ContentScale.Crop,
                        contentDescription = "reviews"
                    )

                    /* val painterResource =
                         asyncPainterResource(data = "https://image.tmdb.org/t/p/original$it")
                     KamelImage(
                         modifier = Modifier
                             .width(200.dp)
                             .height(200.dp),
                         resource = painterResource,
                         contentScale = ContentScale.Crop,
                         contentDescription = "reviews",
                         animationSpec = tween()
                     )*/
                }
            }
        }

        if (backdrops.isNotEmpty()) {
            ShowHeaderText("Backdrops")
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 5.dp)
                    .height(200.dp),
                horizontalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                items(backdrops){
                    val painterResource = rememberAsyncImagePainter("https://image.tmdb.org/t/p/original$it")

                    Image(
                        painter = painterResource,
                        modifier = Modifier
                            .width(300.dp)
                            .height(200.dp),
                        contentScale = ContentScale.Crop,
                        contentDescription = "reviews"
                    )

                    /*val painterResource =
                        asyncPainterResource(data = "https://image.tmdb.org/t/p/original$it")
                    KamelImage(
                        modifier = Modifier
                            .width(300.dp)
                            .height(200.dp),
                        resource = painterResource,
                        contentScale = ContentScale.Crop,
                        contentDescription = "reviews",
                        animationSpec = tween()
                    )*/
                }
            }
        }
    }
}
/*
@Composable
fun ShowVideos(videos: List<Videos>) {
    if(videos.isNotEmpty()){
        ShowHeaderText(title = "Videos")
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(5.dp)
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            videos.forEach { videoId ->
                Box(modifier = Modifier.width(250.dp)) {
                    AndroidView(factory = {
                        val view = YouTubePlayerView(it)
                        view.addYouTubePlayerListener(
                            object : AbstractYouTubePlayerListener() {
                                override fun onReady(youTubePlayer: YouTubePlayer) {
                                    super.onReady(youTubePlayer)
                                    youTubePlayer.cueVideo(videoId.key, 0f)
                                }
                            }
                        )
                        view
                    })
                }
            }
        }
    }

}*/

@Composable
fun ShowReviews(reviews: List<MovieReview>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
    ) {
        ShowHeaderText(title = "Reviews(${reviews.size})")
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 5.dp),
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            items(reviews, key = {it.url}){
                ShowReviewCards(it)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalResourceApi::class)
@Composable
fun ShowReviewCards(review: MovieReview) {
    Card(modifier = Modifier
        .width(350.dp)
        .height(120.dp),
        onClick = { }
    ) {
        Row {

            val painterResource = rememberAsyncImagePainter("https://image.tmdb.org/t/p/original${review.avatarPath}")

            Image(
                painter = painterResource,
                modifier = Modifier
                    .width(50.dp)
                    .height(50.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop,
                contentDescription = "reviews"
            )

            /*val painterResource =
                asyncPainterResource(data = "https://image.tmdb.org/t/p/original${review.avatarPath}")
            KamelImage(
                modifier = Modifier
                    .width(50.dp)
                    .height(50.dp)
                    .clip(CircleShape),
                resource = painterResource,
                contentScale = ContentScale.Crop,
                contentDescription = "reviews",
                animationSpec = tween()
            )*/
            Column(modifier = Modifier.padding(5.dp)) {
                Row {
                    Text(text = review.title)
                    Row(
                        modifier = Modifier
                            .padding(start = 7.dp)
                            .background(Color.Blue, RoundedCornerShape(10.dp))
                            .padding(2.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = review.rating.toString(),
                            color = Color.White,
                            fontSize = 12.sp
                        )
                        Image(
                            modifier = Modifier.padding(start = 3.dp, end = 3.dp),
                            painter = painterResource("star.png"),
                            contentDescription =
                            "ratestar"
                        )
                    }
                }
                Text(text = review.updatedAt)
            }
        }
        println("what ${review.content}")
        Text(
            text = review.content,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .widthIn(max = 350.dp)
                .padding(start = 5.dp)
        )
    }
}

@Composable
fun ShowMovieInfo(movieInfo: MovieInfo) {
    Row(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .padding(5.dp),
        horizontalArrangement = Arrangement.SpaceBetween

    ) {
        MovieInfoText("Status", movieInfo.status)

        MovieInfoText("Language", movieInfo.language)

        MovieInfoText("Budget", movieInfo.movieBudget)

        MovieInfoText("Revenue", movieInfo.movieRevenue)
    }
}

@Composable
fun MovieInfoText(title: String, data: String) {
    Column(modifier = Modifier.padding(3.dp)) {
        Text(
            text = title,
            color = Color.DarkGray,
            style = TextStyle(
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )
        )

        Text(
            text = data,
            color = Color.Gray,
            style = TextStyle(
                fontSize = 10.sp,
                fontWeight = FontWeight.Medium
            )
        )
    }
}

@Composable
private fun ShowOverview(overview: String, vote: Double) {
    val annotatedString = buildAnnotatedString {
        appendInlineContent(id = "imageId")
        append(overview)
    }
    val inlineContentMap = mapOf(
        "imageId" to InlineTextContent(
            Placeholder(40.sp, 40.sp, PlaceholderVerticalAlign.TextCenter)
        ) {
            AddVoteProgressBar(voteAverage = vote)
        }
    )

    Text(
        text = annotatedString,
        inlineContent = inlineContentMap,
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp),
    )
}

@Composable
private fun ShowImages(movieDetailsModel: MovieDetailsModel) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
        contentAlignment = Alignment.CenterStart
    ) {

        val painterResource = rememberAsyncImagePainter("https://image.tmdb.org/t/p/original${movieDetailsModel.backDropImage}")

        Image(
            painter = painterResource,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .alpha(0.3f),
            contentScale = ContentScale.FillWidth,
            contentDescription = "movieImages"
        )

        /*val painterResource =
            asyncPainterResource(data = "https://image.tmdb.org/t/p/original${movieDetailsModel.backDropImage}")
        KamelImage(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .alpha(0.3f),
            resource = painterResource,
            contentScale = ContentScale.FillWidth,
            contentDescription = "movieImages",
            animationSpec = tween()
        )*/

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
        ) {
            val visibleState = remember {
                MutableTransitionState(false).apply {
                    targetState = true // start the animation immediately
                }
            }
            val density = LocalDensity.current
            AnimatedVisibility(
                visibleState = visibleState,
                enter = slideInVertically {
                    // Slide in from 40 dp from the top.
                    with(density) { -200.dp.roundToPx() }
                }
            ) {

                val painterResource = rememberAsyncImagePainter("https://image.tmdb.org/t/p/original${movieDetailsModel.posterImage}")

                Image(
                    painter = painterResource,
                    modifier = Modifier
                        .width(120.dp)
                        .height(180.dp)
                        .padding(start = 5.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .zIndex(10f),
                    contentScale = ContentScale.Crop,
                    contentDescription = "movieImages"
                )

                /*val painterResource =
                    asyncPainterResource(data = "https://image.tmdb.org/t/p/original${movieDetailsModel.posterImage}")
                KamelImage(
                    modifier = Modifier
                        .width(120.dp)
                        .height(180.dp)
                        .padding(start = 5.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .zIndex(10f),
                    resource = painterResource,
                    contentScale = ContentScale.Crop,
                    contentDescription = "poster image",
                    animationSpec = tween()
                )*/
            }
            Column(modifier = Modifier.padding(5.dp)) {
                Text(
                    text = movieDetailsModel.title,
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
                ShowMovieDetails(
                    movieDetailsModel.releaseDate,
                    movieDetailsModel.genres,
                    movieDetailsModel.duration.toMeaningfulDuration()
                )
            }

        }

    }

}

@Composable
fun ShowMovieDetails(date: String, generes: String, duration: String) {
    val bullet = "\u2022"
    val paragraphStyle = ParagraphStyle(
        textIndent = TextIndent(restLine = 5.sp)
    )
    val textModifier = Modifier
        .fillMaxWidth()

    Text(
        buildAnnotatedString {
            withStyle(
                style = paragraphStyle
            ) {
                append(date)
                append("\t")
                append(bullet)
                append("\t")
                append(generes)
                append("\t")
                append(bullet)
                append("\t")
                append(duration)
            }
        },
        modifier = textModifier,
    )
}

fun Int.toMeaningfulDuration(): String {
    val hours = this / 60
    val minutes = this % 60
    return if (hours == 0) "${minutes}m" else "${hours}h ${minutes}m"
}
