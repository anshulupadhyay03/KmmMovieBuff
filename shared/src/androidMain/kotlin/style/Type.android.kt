package style

import androidx.compose.ui.text.font.FontFamily
import moviebuff.shared.generated.resources.Res
import moviebuff.shared.generated.resources.Tajawal_Medium
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.FontResource

@androidx.compose.runtime.Composable
@OptIn(ExperimentalResourceApi::class)
actual fun getTajawalMediumFontFamily(): FontFamily {
    return FontFamily(
        Font(
            Res.font.Tajawal_Medium
        )
    )
}