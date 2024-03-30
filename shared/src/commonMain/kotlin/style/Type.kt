package style

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import moviebuff.shared.generated.resources.Res
import moviebuff.shared.generated.resources.Tajawal_Light
import moviebuff.shared.generated.resources.Tajawal_Medium
import moviebuff.shared.generated.resources.Tajawal_Regular
import org.jetbrains.compose.resources.ExperimentalResourceApi


// Set of Material typography styles to start with
@Composable
@OptIn(ExperimentalResourceApi::class)
fun getTypography() = Typography(
    bodyLarge = TextStyle(
        fontFamily = getTajawalMediumFontFamily(),
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
/*titleLarge = TextStyle(
    fontFamily = getFontFamily(Res.font.Tajawal_Regular),
    fontWeight = FontWeight.Bold,
    fontSize = 22.sp,
    lineHeight = 28.sp,
    letterSpacing = 0.sp
),
labelSmall = TextStyle(
    fontFamily = getFontFamily(Res.font.Tajawal_Light),
    fontWeight = FontWeight.Medium,
    fontSize = 11.sp,
    lineHeight = 16.sp,
    letterSpacing = 0.5.sp
)*/
)
@Composable
expect fun getTajawalMediumFontFamily(): FontFamily