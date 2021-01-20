/*
 * Copyright (c) 2021. Cognizant Softvision
 * Krisztian Bokor
 *
 */

package com.softvision.krisztianbokor.data.datasource.local

import com.softvision.krisztianbokor.data.datasource.local.DateConstants.FORMATTER_FORMAT
import com.softvision.krisztianbokor.data.datasource.local.DateConstants.PARSER_FORMAT
import java.text.SimpleDateFormat

private object DateConstants {
    const val PARSER_FORMAT = "yyyy-MM-dd'T'HH:mm:ss"
    const val FORMATTER_FORMAT = "dd.MM.yyyy HH:mm"
}

fun getFormattedDate(date: String): String {
    val parser = SimpleDateFormat(PARSER_FORMAT)
    val formatter = SimpleDateFormat(FORMATTER_FORMAT)
    return try {
        formatter.format(parser.parse(date))
    } catch (e: Exception) {
        date
    }
}

fun areFieldsValid(
    firstName: String, lastName: String, issueCount: Int?, dateOfBirth: String
): Boolean {
    if (firstName.isEmpty() || lastName.isEmpty() || issueCount == null || dateOfBirth.isEmpty())
        return false
    return true
}
