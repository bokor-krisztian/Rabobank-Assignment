/*
 * Copyright (c) 2021. Cognizant Softvision
 * Krisztian Bokor
 *
 */

package com.softvision.krisztianbokor.domain.usecase

import com.softvision.krisztianbokor.app.personModule
import com.softvision.krisztianbokor.domain.model.PersonModel
import com.softvision.krisztianbokor.util.RaboInstrumentationTestRunner
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import org.koin.java.KoinJavaComponent.inject

@ExperimentalCoroutinesApi
class GetPersonsUseCaseInstrumentedTest : RaboInstrumentationTestRunner() {

    val SUT: GetPersonsUseCase by inject(GetPersonsUseCase::class.java)

    @Before
    fun setUp() {
        loadKoinModules(personModule)
    }

    @After
    fun tearDown() {
        unloadKoinModules(personModule)
    }

    @Test
    fun testUseCase_listOfPersons_shouldReturnOK() {
        runBlocking {
            SUT.invoke().collect { result ->
                assertTrue(result is GetPersonsResult.HasData)
                assertEquals((result as GetPersonsResult.HasData).data, getListOfPersons())
            }
        }
    }

    private fun getListOfPersons(): List<PersonModel> =
        listOf(
            PersonModel("Theo", "Jansen", 5, "02.01.1978 00:00"),
            PersonModel("Fiona", "de Vries", 7, "12.11.1950 00:00"),
            PersonModel("Petra", "Boersma", 1, "20.04.2001 00:00")
        )
}