package ui.features

import LocalAppConfiguration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SuggestionChip
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
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import coil3.compose.AsyncImagePainter
import coil3.compose.LocalPlatformContext
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
import coil3.size.Size
import decompose.DetailsScreenComponent
import domain.MovieCast
import domain.MovieDetailsModel
import domain.MovieDetailsUiState
import domain.MovieInfo
import domain.MovieReview
import moviebuff.shared.generated.resources.Res
import moviebuff.shared.generated.resources.arrow_back
import moviebuff.shared.generated.resources.ic_duration
import moviebuff.shared.generated.resources.reviewer_avtar
import moviebuff.shared.generated.resources.star
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import ui.features.web.ShowWebDetailsLayout


@Composable
fun MovieDetailsScreen(component: DetailsScreenComponent) {

    val movieDetailState by component.viewModel.movieDetailsState.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center
    ) {
        when (movieDetailState) {
            MovieDetailsUiState.Loading -> {
                LoadingItem()
            }

            is MovieDetailsUiState.Success -> {
                ShowMovieDetails((movieDetailState as MovieDetailsUiState.Success).movieDetails) {
                    component.onBackPressed()
                }
            }

            else -> {
                ErrorItem("Something went wrong") {}
            }
        }
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun ShowCasts(casts: List<MovieCast>) {
    AddTitleAndDivider("Top Actors")
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
    ) {
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .padding(top = 5.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(casts) {
                Column(
                    modifier = Modifier
                        .padding(start = 5.dp, end = 5.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    val painterResource =
                        rememberAsyncImagePainter(
                            ImageRequest.Builder(LocalPlatformContext.current)
                                .data("https://image.tmdb.org/t/p/original${it.avatarPath}")
                                .size(Size.ORIGINAL)
                                .build()
                        )


                     when(painterResource.state){
                         is AsyncImagePainter.State.Loading -> {
                             CircularProgressIndicator()
                         }
                         is AsyncImagePainter.State.Error -> {
                             Image(
                                 painter = painterResource(Res.drawable.reviewer_avtar),
                                 modifier = Modifier
                                     .width(80.dp)
                                     .height(100.dp)
                                     .clip(RectangleShape)
                                     .border(2.dp, color = Color.DarkGray),
                                 contentScale = ContentScale.FillBounds,
                                 contentDescription = "reviews"
                             )
                         }
                         is AsyncImagePainter.State.Success -> {
                             Image(
                                 painter = painterResource,
                                 modifier = Modifier
                                     .width(80.dp)
                                     .height(100.dp)
                                     .clip(RectangleShape)
                                     .border(2.dp, color = Color.DarkGray),
                                 contentScale = ContentScale.Crop,
                                 contentDescription = "reviews"
                             )
                         }

                         else -> {}
                     }
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
        fontWeight = FontWeight.Bold
    )
}

@Composable
fun ShowKeyWords(keywords: List<String>) {
    AddTitleAndDivider("Keywords")
    Row(modifier = Modifier.horizontalScroll(rememberScrollState())) {
        keywords.forEach { word ->
            println("word : $word")
            SuggestionChip(
                onClick = {},
                label = { Text(word) },
                modifier = Modifier.padding(2.dp)
            )
        }
    }
}

@Composable
fun AddTitleAndDivider(sectionTitle: String) {
    Column(modifier = Modifier.padding(5.dp)) {
        ShowHeaderText(sectionTitle)
        HorizontalDivider()
    }

}

@Composable
fun ShowMovieDetails(movieDetailsModel: MovieDetailsModel, onBackPressed: () -> Unit) {
    if (LocalAppConfiguration.current.isWeb) {
        ShowWebDetailsLayout(movieDetailsModel, onBackPressed)
    } else {
        ShowMobileDetailsLayout(movieDetailsModel, onBackPressed)
    }
}

@Composable
private fun ShowMobileDetailsLayout(
    movieDetailsModel: MovieDetailsModel,
    onBackPressed: () -> Unit
) {
    ShowImages(movieDetailsModel, onBackPressed)
    HorizontalDivider()
    ShowOverview(movieDetailsModel.overview, movieDetailsModel.vote)
    HorizontalDivider()
    ShowMovieInfo(movieDetailsModel.movieInfo)
    ShowKeyWords(movieDetailsModel.keywords)
    if (movieDetailsModel.reviews.isNotEmpty()) {
        ShowReviews(movieDetailsModel.reviews)
    }

    ShowCasts(movieDetailsModel.topCast)
    ShowPostersAndBackDrops(movieDetailsModel.posters, movieDetailsModel.backdrops)
    //ShowVideos(movieDetailsModel.videos)
}

@Composable
fun ShowPostersAndBackDrops(posters: List<String>, backdrops: List<String>) {
    Column(modifier = Modifier.padding(5.dp)) {
        if (posters.isNotEmpty()) {
            AddTitleAndDivider("Posters")
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                horizontalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                items(posters) {
                    val painterResource =
                        rememberAsyncImagePainter("https://image.tmdb.org/t/p/original$it")

                    Image(
                        painter = painterResource,
                        modifier = Modifier
                            .width(200.dp)
                            .height(200.dp),
                        contentScale = ContentScale.Crop,
                        contentDescription = "reviews"
                    )
                }
            }
        }

        if (backdrops.isNotEmpty()) {
            AddTitleAndDivider("Backdrops")
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 5.dp)
                    .height(200.dp),
                horizontalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                items(backdrops) {
                    val painterResource =
                        rememberAsyncImagePainter("https://image.tmdb.org/t/p/original$it")

                    Image(
                        painter = painterResource,
                        modifier = Modifier
                            .width(300.dp)
                            .height(200.dp),
                        contentScale = ContentScale.Crop,
                        contentDescription = "reviews"
                    )
                }
            }
        }
    }
}

@Composable
fun ShowReviews(reviews: List<MovieReview>) {
    AddTitleAndDivider("Reviews(${reviews.size})")
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
    ) {
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 5.dp),
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            items(reviews, key = { it.url }) {
                ShowReviewCards(it)
            }
        }
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun ShowReviewCards(review: MovieReview) {
    Card(modifier = Modifier
        .width(350.dp)
        .height(120.dp),
        onClick = { }
    ) {
        Row {

            val painterResource =
                rememberAsyncImagePainter(
                    ImageRequest.Builder(LocalPlatformContext.current)
                        .data("https://image.tmdb.org/t/p/original${review.avatarPath}")
                        .size(Size.ORIGINAL)
                        .build()

                )

            if(painterResource.state is AsyncImagePainter.State.Loading){
                CircularProgressIndicator()
            }else {
                Image(
                    painter = painterResource,
                    modifier = Modifier
                        .width(50.dp)
                        .height(50.dp)
                        .clip(RoundedCornerShape(10.dp)),
                    contentScale = ContentScale.Crop,
                    contentDescription = "reviews"
                )
            }
            Column(modifier = Modifier.padding(5.dp)) {
                Row {
                    Text(text = review.title)
                    Row(
                        modifier = Modifier
                            .padding(start = 7.dp)
                            .background(
                                color = MaterialTheme.colorScheme.primary,
                                shape = RoundedCornerShape(10.dp)
                            )
                            .padding(2.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = review.rating.toString(),
                            color = MaterialTheme.colorScheme.onSecondary,
                            fontSize = 12.sp
                        )
                        Image(
                            modifier = Modifier.padding(start = 3.dp, end = 3.dp),
                            painter = painterResource(Res.drawable.star),
                            contentDescription =
                            "ratestar"
                        )
                    }
                }
                Text(text = review.updatedAt)
            }
        }
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
            style = TextStyle(
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )
        )

        Text(
            text = data,
            style = TextStyle(
                fontSize = 10.sp
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
        color = MaterialTheme.colorScheme.onBackground,
        inlineContent = inlineContentMap,
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp),
    )
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun ShowBackArrow(onBackPressed: () -> Unit) {
    IconButton(
        onClick = {
            onBackPressed.invoke()
        }) {
        Icon(
            painterResource(Res.drawable.arrow_back),
            contentDescription = null
        )
    }
}

@Composable
private fun ShowImages(movieDetailsModel: MovieDetailsModel, onBackPressed: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
    ) {

        ShowBackArrow {
            onBackPressed.invoke()
        }

        val painterResource =
            rememberAsyncImagePainter(
                ImageRequest.Builder(LocalPlatformContext.current)
                    .data("https://image.tmdb.org/t/p/original${movieDetailsModel.backDropImage}")
                    .size(Size.ORIGINAL)
                    .build()
            )

        when(painterResource.state){
            is AsyncImagePainter.State.Loading->{
                CircularProgressIndicator()
            }

            is AsyncImagePainter.State.Success ->{
                Image(
                    painter = painterResource,
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .alpha(0.3f),
                    contentScale = ContentScale.Crop,
                    contentDescription = "movieImages"
                )
            }
            else -> {}
        }


        Row(
            modifier = Modifier
                .padding(top = 50.dp)
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

                val sideImageRes =
                    rememberAsyncImagePainter("https://image.tmdb.org/t/p/original${movieDetailsModel.posterImage}")

                Image(
                    painter = sideImageRes,
                    modifier = Modifier
                        .width(120.dp)
                        .height(180.dp)
                        .padding(start = 5.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .zIndex(10f),
                    contentScale = ContentScale.Crop,
                    contentDescription = "movieImages"
                )
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

@OptIn(ExperimentalResourceApi::class)
@Composable
fun ShowMovieDetails(date: String, generes: String, duration: String) {
    val bullet = "\u2022"
    val paragraphStyle = ParagraphStyle(
        textIndent = TextIndent(restLine = 5.sp)
    )
    val textModifier = Modifier
        .fillMaxWidth()
        .padding(5.dp)

    Text(
        buildAnnotatedString {
            withStyle(
                style = paragraphStyle
            ) {
                append(date)
                append(bullet)
                append(generes)
            }
        },
        fontSize = 20.sp,
        modifier = textModifier,
    )

    Row(
        modifier = Modifier
            .padding(2.dp)
            .border(
                border = ButtonDefaults.outlinedButtonBorder,
                shape = RoundedCornerShape(4.dp),

                )
            .padding(2.dp)
    ) {
        Image(
            painterResource(Res.drawable.ic_duration),
            "movie duration",
            modifier = Modifier.align(Alignment.CenterVertically)
        )
        Text(
            modifier = Modifier.align(Alignment.CenterVertically).padding(start = 2.dp),
            text = duration,
            fontSize = 14.sp
        )
    }

}

fun Int.toMeaningfulDuration(): String {
    val hours = this / 60
    val minutes = this % 60
    return if (hours == 0) "${minutes}m" else "${hours}h ${minutes}m"
}
