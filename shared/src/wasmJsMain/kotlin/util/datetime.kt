package util

actual fun String.format(format: String): String {
    /*val date = getDateFromIso8601Timestamp(this) ?: return ""

    val dateFormatter = NSDateFormatter()
    dateFormatter.timeZone = NSTimeZone.localTimeZone
    dateFormatter.locale = NSLocale.autoupdatingCurrentLocale
    dateFormatter.dateFormat = format
    return dateFormatter.stringFromDate(date)*/

    return "03-03-1987"
}

/*
private fun getDateFromIso8601Timestamp(string: String): NSDate? {
    return NSISO8601DateFormatter().dateFromString(string)
}*/
