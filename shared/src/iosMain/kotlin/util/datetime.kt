package util

import platform.Foundation.NSDateFormatter

import kotlinx.datetime.LocalDateTime
import platform.Foundation.*

actual fun String.format(format: String): String {
    val date = getDateFromIso8601Timestamp(this) ?: return ""

    val dateFormatter = NSDateFormatter()
    dateFormatter.timeZone = NSTimeZone.localTimeZone
    dateFormatter.locale = NSLocale.autoupdatingCurrentLocale
    dateFormatter.dateFormat = format
    return dateFormatter.stringFromDate(date)
}

private fun getDateFromIso8601Timestamp(string: String): NSDate? {
    return NSISO8601DateFormatter().dateFromString(string)
}