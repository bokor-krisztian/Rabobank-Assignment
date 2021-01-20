/*
 * Copyright (c) 2021. Cognizant Softvision
 * Krisztian Bokor
 *
 */

package com.softvision.krisztianbokor.data.datasource.local

import android.content.Context
import com.softvision.krisztianbokor.R
import com.softvision.krisztianbokor.data.datasource.getIndexOf
import com.softvision.krisztianbokor.data.datasource.splitAndTrimItems
import com.softvision.krisztianbokor.domain.exception.CsvParsingException
import com.softvision.krisztianbokor.domain.model.PersonModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.nio.charset.Charset
import java.text.SimpleDateFormat

class PersonLocalDataSource(private val context: Context) {

    fun getPersons(): Flow<List<PersonModel>> = flow {
        try {
            val csvReader = CoroutineScope(Dispatchers.IO).async { getCsvFileAsBufferedReader() }
            val persons = getParsedPersons(csvReader.await())
            emit(persons)
        } catch (e: Exception) {
            throw CsvParsingException(e.message)
        }
    }

    private fun getCsvFileAsBufferedReader(): BufferedReader {
        val inputStream: InputStream = context.resources.openRawResource(R.raw.issues)
        return BufferedReader(InputStreamReader(inputStream, Charset.forName("UTF-8")))
    }

    private fun getParsedPersons(csvReader: BufferedReader): List<PersonModel> {
        val personsList = ArrayList<PersonModel>()
        val headerItems = csvReader.readLine().splitAndTrimItems()

        val firstNameIndex = headerItems.getIndexOf(PersonModel.FIRST_NAME)
        val lastNameIndex = headerItems.getIndexOf(PersonModel.LAST_NAME)
        val issueCountIndex = headerItems.getIndexOf(PersonModel.ISSUE_COUNT)
        val dateOfBirthIndex = headerItems.getIndexOf(PersonModel.DATE_OF_BIRTH)

        if (firstNameIndex == -1 || lastNameIndex == -1 || issueCountIndex == -1 || dateOfBirthIndex == -1) {
            throw CsvParsingException("Invalid format for CSV file")
        }

        csvReader.readLines().forEach {
            val items = it.splitAndTrimItems()

            val firstName = items[firstNameIndex]
            val lastName = items[lastNameIndex]
            val issueCount = items[issueCountIndex].toIntOrNull()
            val dateOfBirth = items[dateOfBirthIndex]

            if (areFieldsValid(firstName, lastName, issueCount, dateOfBirth)) {
                val person = PersonModel(firstName, lastName, issueCount!!, formatDate(dateOfBirth))
                personsList.add(person)
            }
        }
        return personsList
    }

    private fun formatDate(dateOfBirth: String): String {
        val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        val formatter = SimpleDateFormat("dd.MM.yyyy HH:mm")
        return try {
            formatter.format(parser.parse(dateOfBirth))
        } catch (e : Exception) {
            dateOfBirth
        }
    }

    private fun areFieldsValid(
        firstName: String,
        lastName: String,
        issueCount: Int?,
        dateOfBirth: String
    ): Boolean {
        if (firstName.isEmpty() || lastName.isEmpty() || issueCount == null || dateOfBirth.isEmpty())
            return false
        return true
    }


}