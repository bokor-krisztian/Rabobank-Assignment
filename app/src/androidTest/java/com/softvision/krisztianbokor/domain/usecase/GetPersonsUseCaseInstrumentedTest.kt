/*
 * Copyright (c) 2021. Cognizant Softvision
 * Krisztian Bokor
 *
 */

package com.softvision.krisztianbokor.domain.usecase

import android.content.Context
import com.softvision.krisztianbokor.app.personModule
import com.softvision.krisztianbokor.domain.exception.CsvParsingException
import com.softvision.krisztianbokor.domain.model.PersonModel
import com.softvision.krisztianbokor.util.RaboInstrumentationTestRunner
import com.softvision.krisztianbokor.util.personTestModule
import io.mockk.every
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import org.koin.java.KoinJavaComponent.inject

@ExperimentalCoroutinesApi
class GetPersonsUseCaseInstrumentedTest : RaboInstrumentationTestRunner() {

    private val SUT: GetPersonsUseCase by inject(GetPersonsUseCase::class.java)

    private val mockContext: Context by inject(Context::class.java)

    @Test
    fun testUseCase_listOfPersons_shouldReturnOK() {
        loadKoinModules(personModule)
        runBlocking {
            SUT.invoke().collect { result ->
                assertTrue(result is GetPersonsResult.HasData)
                assertEquals((result as GetPersonsResult.HasData).data.size, 3)
                assertEquals(result.data, getListOfPersons())
            }
        }
        unloadKoinModules(personModule)
    }

    @Test
    fun testUseCase_exception_shouldReturnError() {
        loadKoinModules(personTestModule)
        runBlocking {
            every { mockContext.resources.openRawResource(any()) } throws CsvParsingException()

            SUT.invoke().collect { result ->
                assertTrue(result is GetPersonsResult.Error)
            }
        }
        unloadKoinModules(personTestModule)
    }

    private fun getListOfPersons(): List<PersonModel> =
        listOf(
            PersonModel("Theo", "Jansen", 5, "02.01.1978 00:00"),
            PersonModel("Fiona", "de Vries", 7, "12.11.1950 00:00"),
            PersonModel("Petra", "Boersma", 1, "20.04.2001 00:00")
        )
}
