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
import androidx.compose.ui.layout.layout
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import style.Green
import style.LightGreen
import style.LightYellow
import style.Yellow

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
            .fillMaxHeight(),
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
