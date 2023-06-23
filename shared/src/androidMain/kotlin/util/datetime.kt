package util

import android.os.Build
import androidx.annotation.RequiresApi
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toJavaLocalDateTime
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
actual fun String.format(format: String): String {
    val date = getDateFromIso8601Timestamp(this)
    val formatter = DateTimeFormatter.ofPattern(format)
    return date.format(formatter)

}

@RequiresApi(Build.VERSION_CODES.O)
private fun getDateFromIso8601Timestamp(isoDate: String) = ZonedDateTime.parse(isoDate)