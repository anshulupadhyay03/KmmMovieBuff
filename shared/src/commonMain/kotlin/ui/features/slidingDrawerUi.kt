package ui.features

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.seiko.imageloader.rememberAsyncImagePainter
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@Composable
fun UserImageArea() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .border(2.dp, Color.Gray)
    ) {
        Row(
        ) {
            val painterResource = rememberAsyncImagePainter("https://www.themoviedb.org/t/p/w600_and_h900_bestv2/rQtUWshSz2no41OXniyNJxSoBhf.jpg")

            Image(
                painter = painterResource,
                modifier = Modifier
                    .width(120.dp)
                    .height(150.dp),
                contentScale = ContentScale.Crop,
                contentDescription = ""
            )

            Spacer(modifier = Modifier.fillMaxHeight().width(2.dp))

            Column {
                Text("Anshul Upadhyay")
                Text("Joined on : 03 Mar 2021")
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
            .clickable { },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource("outline_home.xml"),
            contentDescription = null
        )

        Spacer(modifier = Modifier.fillMaxHeight().width(10.dp))

        Text(name)
    }
}