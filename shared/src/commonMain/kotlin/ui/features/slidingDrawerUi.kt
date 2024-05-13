package ui.features

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.rememberAsyncImagePainter
import moviebuff.shared.generated.resources.Res
import moviebuff.shared.generated.resources.date_range
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@Composable
fun UserImageArea() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .background(MaterialTheme.colorScheme.primaryContainer)
            .padding(10.dp)
    ) {
        Row(
        ) {
            val painterResource = rememberAsyncImagePainter("https://www.themoviedb.org/t/p/w600_and_h900_bestv2/rQtUWshSz2no41OXniyNJxSoBhf.jpg")

            Image(
                painter = painterResource,
                modifier = Modifier
                    .width(120.dp)
                    .height(150.dp)
                    .clip(CircleShape)
                    .aspectRatio(1f),
                contentScale = ContentScale.Crop,
                contentDescription = ""
            )

            Spacer(modifier = Modifier.fillMaxHeight().width(2.dp))

            Column(modifier = Modifier.padding(10.dp)) {
                Text("Jenna Lee" , style = TextStyle(
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                ))
                Text("Member Since : 03 Mar, 2021",
                    style = TextStyle(
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        fontSize = 14.sp
                    ))
            }
        }
    }
}

private val options = arrayListOf("Favorite", "Settings","Dark theme", "Actors","Watchlist", "Settings")

@Composable
fun DrawerOptions() {
    Column {
        options.forEach {
            OptionItem(it)
        }
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun OptionItem(name: String) {
    Row(
        modifier =
        Modifier
            .fillMaxWidth()
            .height(60.dp)
            .padding(10.dp)
            .background(MaterialTheme.colorScheme.surfaceVariant, shape = RoundedCornerShape(5.dp))
            .clickable {},
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(Res.drawable.date_range),
            contentDescription = null
        )

        Spacer(modifier = Modifier.fillMaxHeight().width(10.dp))

        Text(name)
    }
}