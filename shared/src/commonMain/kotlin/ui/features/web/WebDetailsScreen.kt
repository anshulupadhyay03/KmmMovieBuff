package ui.features.web

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.InputChip
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import coil3.compose.rememberAsyncImagePainter
import domain.MovieDetailsModel
import domain.MovieInfo
import ui.features.AddTitleAndDivider
import ui.features.AddVoteProgressBar
import ui.features.ShowBackArrow
import ui.features.ShowCasts
import ui.features.ShowPostersAndBackDrops
import ui.features.ShowReviews
import ui.features.toMeaningfulDuration

@Composable
fun ShowWebDetailsLayout(movieDetailsModel: MovieDetailsModel, onBackPressed: () -> Unit) {
    ShowImages(movieDetailsModel, onBackPressed)
    Row(modifier = Modifier.padding(5.dp)) {
        Row(
            Modifier.weight(2f).padding(start = 2.dp)
        ) {
            LeftPart(movieDetailsModel)
        }
        VerticalDivider(thickness = 2.dp)
        Row(
            Modifier.weight(0.5f).padding(end = 2.dp)
        ) {
            ShowMovieInfo(movieDetailsModel.movieInfo, movieDetailsModel.keywords)
        }
    }
}

@Composable
fun ShowMovieInfo(movieInfo: MovieInfo, keywords: List<String>) {
    Column(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .padding(5.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
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
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        )

        Text(
            text = data,
            style = TextStyle(
                fontSize = 14.sp
            )
        )
    }
}

@Composable
fun ShowKeyWords(keywords: List<String>) {
    AddTitleAndDivider("Keywords")
    Row(modifier = Modifier
        .horizontalScroll(rememberScrollState())
    ) {
        keywords.forEach { word ->
            InputChip(
                modifier = Modifier.padding(5.dp),
                selected = false,
                onClick = {},
                label = { Text(word) }
            )
        }
    }
}

@Composable
fun LeftPart(movieDetailsModel: MovieDetailsModel) {
    Column {
        ShowKeyWords(movieDetailsModel.keywords)
        if (movieDetailsModel.reviews.isNotEmpty()) {
            ShowReviews(movieDetailsModel.reviews)
        }

        ShowCasts(movieDetailsModel.topCast)
        ShowPostersAndBackDrops(movieDetailsModel.posters, movieDetailsModel.backdrops)
    }
}

@Composable
private fun ShowImages(movieDetailsModel: MovieDetailsModel, onBackPressed: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(500.dp)
    ) {

        ShowBackArrow {
            onBackPressed.invoke()
        }

        val painterResource =
            rememberAsyncImagePainter("https://image.tmdb.org/t/p/original${movieDetailsModel.backDropImage}")

        Image(
            painter = painterResource,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .alpha(0.3f),
            contentScale = ContentScale.Crop,
            contentDescription = "movieImages"
        )
        Row(
            modifier = Modifier
                .padding(top = 50.dp)
                .fillMaxWidth()
                .height(400.dp)

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
                        .width(250.dp)
                        .height(400.dp)
                        .padding(start = 5.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .zIndex(10f),
                    contentScale = ContentScale.Crop,
                    contentDescription = "movieImages"
                )
            }
            Column(modifier = Modifier.padding(20.dp)) {
                Text(
                    modifier = Modifier.padding(bottom = 10.dp),
                    text = movieDetailsModel.title,
                    style = TextStyle(
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
                ShowMovieDetails(
                    movieDetailsModel.releaseDate,
                    movieDetailsModel.genres,
                    movieDetailsModel.duration.toMeaningfulDuration()
                )

                Spacer(Modifier.padding(10.dp))
                AddVoteProgressBar(movieDetailsModel.vote)

                Spacer(Modifier.padding(10.dp))
                Text(
                    modifier = Modifier.padding(10.dp),
                    text = "Overview",
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    modifier = Modifier.padding(10.dp),
                    text = movieDetailsModel.overview,
                    fontSize = 22.sp
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
                append(" ")
                append(bullet)
                append(" ")
                append(generes)
                append(" ")
                append(bullet)
                append(" ")
                append(duration)

            }
        },
        fontSize = 20.sp,
        modifier = textModifier,
    )
}
