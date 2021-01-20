/*
 * Copyright (c) 2021. Cognizant Softvision
 * Krisztian Bokor
 *
 */

package com.softvision.krisztianbokor.data.datasource.local

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.softvision.krisztianbokor.R
import com.softvision.krisztianbokor.domain.exception.CsvParsingException
import com.softvision.krisztianbokor.domain.model.PersonModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.io.ByteArrayInputStream
import java.io.File
import java.io.InputStream

@ExperimentalCoroutinesApi
class PersonLocalDataSourceUnitTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @InjectMockKs
    lateinit var SUT: PersonLocalDataSource

    @MockK
    lateinit var context: Context

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test(expected = CsvParsingException::class)
    fun getPersons_invalidCsv_shouldThrowException() {
        runBlocking {
            coEvery { context.resources.openRawResource(R.raw.issues) } coAnswers { getInvalidStream() }

            SUT.getPersons().collect()
        }
    }

    @Test(expected = CsvParsingException::class)
    fun getPersons_simplePdf_shouldThrowException() {
        runBlocking {
            coEvery { context.resources.openRawResource(R.raw.issues) } coAnswers { getTestFile("/sample.pdf") }

            SUT.getPersons().collect()
        }
    }

    @Test
    fun getPersons_simpleCsv_shouldReturnOK() {
        runBlocking {
            coEvery { context.resources.openRawResource(R.raw.issues) } coAnswers { getTestFile("/simple.csv") }

            SUT.getPersons().collect { result ->
                assertEquals(result.size, 3)
                assertEquals(result[0], PersonModel("Theo", "Jansen", 5, "02.01.1978 00:00"))
                assertEquals(result[1], PersonModel("Fiona", "de Vries", 7, "12.11.1950 00:00"))
                assertEquals(result[2], PersonModel("Petra", "Boersma", 1, "20.04.2001 00:00"))
            }
        }
    }

    @Test
    fun getPersons_wrongColumnsOrderCsv_shouldReturnOK() {
        runBlocking {
            coEvery { context.resources.openRawResource(R.raw.issues) } coAnswers { getTestFile("/wrong_column_order.csv") }

            SUT.getPersons().collect { result ->
                assertEquals(result.size, 3)
                assertEquals(result[0], PersonModel("Theo", "Jansen", 5, "02.01.1978 00:00"))
                assertEquals(result[1], PersonModel("Fiona", "de Vries", 7, "12.11.1950 00:00"))
                assertEquals(result[2], PersonModel("Petra", "Boersma", 1, "20.04.2001 00:00"))
            }
        }
    }

    @Test
    fun getPersons_incompleteCsv_shouldReturnOK() {
        runBlocking {
            coEvery { context.resources.openRawResource(R.raw.issues) } coAnswers { getTestFile("/incomplete.csv") }

            SUT.getPersons().collect { result ->
                assertEquals(result.size, 2)
                assertEquals(result[0], PersonModel("Theo", "Jansen", 5, "02.01.1978 00:00"))
                assertEquals(result[1], PersonModel("Fiona", "de Vries", 7, "12.11.1950 00:00"))
            }
        }
    }

    @Test
    fun getPersons_extraColumnsCsv_shouldReturnOK() {
        runBlocking {
            coEvery { context.resources.openRawResource(R.raw.issues) } coAnswers { getTestFile("/extra_columns.csv") }

            SUT.getPersons().collect { result ->
                assertEquals(result.size, 3)
                assertEquals(result[0], PersonModel("Theo", "Jansen", 5, "02.01.1978 00:00"))
                assertEquals(result[1], PersonModel("Fiona", "de Vries", 7, "12.11.1950 00:00"))
                assertEquals(result[2], PersonModel("Petra", "Boersma", 1, "20.04.2001 00:00"))
            }
        }
    }

    private fun getInvalidStream(): InputStream = ByteArrayInputStream("test data".toByteArray())

    private fun getTestFile(fileName : String): InputStream {
        val url = javaClass.getResource(fileName).file;
        val file = File(url);
        return file.inputStream()
    }
}