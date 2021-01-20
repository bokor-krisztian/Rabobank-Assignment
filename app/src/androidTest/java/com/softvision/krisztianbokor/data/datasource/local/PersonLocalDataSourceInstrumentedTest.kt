/*
 * Copyright (c) 2021. Cognizant Softvision
 * Krisztian Bokor
 *
 */

package com.softvision.krisztianbokor.data.datasource.local

import android.content.Context
import android.content.res.Resources.NotFoundException
import com.softvision.krisztianbokor.domain.exception.CsvParsingException
import com.softvision.krisztianbokor.util.RaboInstrumentationTestRunner
import com.softvision.krisztianbokor.util.personTestModule
import io.mockk.every
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import org.koin.java.KoinJavaComponent.inject

@ExperimentalCoroutinesApi
class PersonLocalDataSourceInstrumentedTest : RaboInstrumentationTestRunner() {

    private val SUT: PersonLocalDataSource by inject(PersonLocalDataSource::class.java)

    private val mockContext: Context by inject(Context::class.java)

    @Test
    fun getPersons_simpleCsv_shouldReturnOK() {
        runBlocking {
            SUT.getPersons().collect { result ->
                assertEquals(result.size, 3)
            }
        }
    }

    @Test(expected = CsvParsingException::class)
    fun getPersons_error_shouldReturnException() {
        loadKoinModules(personTestModule)
        runBlocking {
            every { mockContext.resources.openRawResource(any()) } throws NotFoundException()
            SUT.getPersons().collect()
        }
        unloadKoinModules(personTestModule)
    }
}
