package ui.features

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.layout
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import style.Green
import style.LightGreen
import style.LightYellow
import style.Yellow


/*@Composable
fun DisplayMovies(data: LazyPagingItems<MovieResult>, onItemClick: (id: Int) -> Unit) {
    LazyColumn(
        modifier = Modifier.padding(5.dp).background(Color.LightGray),
        state = data.rememberLazyListState()
    ) {
        items(items = data, key = { it.id }) {
            if (it != null) {
                MovieRow(item = it, onItemClick)
            }
        }
        when (data.loadState.append) {
            is LoadState.Error -> {
                item {
                    ErrorItem("Something went wrong!Pleas try again") {
                        data.refresh()
                    }
                }
            }
            is LoadState.Loading -> {
                item {
                    LoadingItem()
                }
            }
            is LoadState.NotLoading -> Unit
        }

        when (data.loadState.refresh) {
            is LoadState.Error -> {
                item {
                    ErrorItem("Something went wrong!Pleas try again") {
                        data.refresh()
                    }
                }
            }
            is LoadState.Loading -> {
                item {
                    LoadingItem()
                }
            }
            is LoadState.NotLoading -> Unit
        }
    }
}*/

enum class RatingBarColors(val highlightColor: Color, val surfaceColor: Color) {
    GREEN(Green, LightGreen),
    YELLOW(Yellow, LightYellow);
}

private fun getRatingBarColors(voteAverage: Double): RatingBarColors {
    return when {
        (voteAverage * 10).toInt() > 70 -> {
            RatingBarColors.GREEN
        }
        else -> RatingBarColors.YELLOW
    }
}

@Composable
fun AddVoteProgressBar(voteAverage: Double) {
    val ratingBarColors = getRatingBarColors(voteAverage)
    Box(contentAlignment = Alignment.Center,
        modifier = Modifier
            .background(Color.Black, shape = CircleShape)
            .padding(1.dp)
            .border(3.dp, ratingBarColors.surfaceColor, CircleShape)
            .layout{ measurable, constraints ->
                // Measure the composable
                val placeable = measurable.measure(constraints)

                //get the current max dimension to assign width=height
                val currentHeight = placeable.height
                val currentWidth = placeable.width
                val newDiameter = maxOf(currentHeight, currentWidth)

                //assign the dimension and the center position
                layout(newDiameter, newDiameter) {
                    // Where the composable gets placed
                    placeable.placeRelative(
                        (newDiameter - currentWidth) / 2, (newDiameter - currentHeight) / 2
                    )
                }

            }) {
        CircularProgressIndicator(
            progress = voteAverage.toFloat() / 10, color = ratingBarColors.highlightColor,
        )

        Text(
            text = "${(voteAverage * 10).toInt()}%",
            textAlign = TextAlign.Center,
            color = Color.White,
            modifier = Modifier.padding(2.dp),
            fontSize = 12.sp,
            style = TextStyle(
                fontWeight = FontWeight.Bold
            )
        )
    }
}


@Composable
fun ErrorItem(error: String, onRefreshClick: () -> Unit) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .padding(5.dp),
        shape = RectangleShape,
        onClick = { onRefreshClick() }) {
        Text(
            textAlign = TextAlign.Center,
            text = error,
            color = Color.Red
        )
    }

}

@Composable
fun LoadingItem() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier
                .width(40.dp)
                .height(40.dp)
                .padding(8.dp)
        )
    }
}

/*@Composable
fun <T : Any> LazyPagingItems<T>.rememberLazyListState(): LazyListState {
    // After recreation, LazyPagingItems first return 0 items, then the cached items.
    // This behavior/issue is resetting the LazyListState scroll position.
    // Below is a workaround. More info: https://issuetracker.google.com/issues/177245496.
    return when (itemCount) {
        // Return a different LazyListState instance.
        0 -> remember(this) { LazyListState(0, 0) }
        // Return rememberLazyListState (normal case).
        else -> androidx.compose.foundation.lazy.rememberLazyListState()
    }
}*/

@Composable
fun ChipVerticalGrid(
    modifier: Modifier = Modifier,
    spacing: Dp,
    content: @Composable () -> Unit
) {
    Layout(
        content = content,
        modifier = modifier
    ) { measurables, constraints ->
        var currentRow = 0
        var currentOrigin = IntOffset.Zero
        val spacingValue = spacing.toPx().toInt()
        val placeables = measurables.map { measurable ->
            val placeable = measurable.measure(constraints)

            if (currentOrigin.x > 0f && currentOrigin.x + placeable.width > constraints.maxWidth) {
                currentRow += 1
                currentOrigin =
                    currentOrigin.copy(x = 0, y = currentOrigin.y + placeable.height + spacingValue)
            }

            placeable to currentOrigin.also {
                currentOrigin = it.copy(x = it.x + placeable.width + spacingValue)
            }
        }

        layout(
            width = constraints.maxWidth,
            height = placeables.lastOrNull()?.run { first.height + second.y } ?: 0
        ) {
            placeables.forEach {
                val (placeable, origin) = it
                placeable.place(origin.x, origin.y)
            }
        }
    }
}