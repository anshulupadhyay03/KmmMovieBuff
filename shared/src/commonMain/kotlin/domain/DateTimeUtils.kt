package domain

import util.format

fun String.dateFormat(): String {
    val dateFormat = "dd-MMM-yy"
    return this.format(dateFormat)
}