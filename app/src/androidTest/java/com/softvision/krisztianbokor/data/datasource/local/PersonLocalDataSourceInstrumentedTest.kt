/*
 * Copyright (c) 2021. Cognizant Softvision
 * Krisztian Bokor
 *
 */

package com.softvision.krisztianbokor.data.datasource.local

import com.softvision.krisztianbokor.app.personModule
import com.softvision.krisztianbokor.util.RaboInstrumentationTestRunner
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import org.koin.java.KoinJavaComponent.inject

@ExperimentalCoroutinesApi
class PersonLocalDataSourceInstrumentedTest : RaboInstrumentationTestRunner() {

    val SUT: PersonLocalDataSource by inject(PersonLocalDataSource::class.java)

    @Before
    fun setUp() {
        loadKoinModules(personModule)
    }

    @After
    fun tearDown() {
        unloadKoinModules(personModule)
    }

    @Test
    fun getPersons_csv100k_shouldReturnOK() {
        runBlocking {
            SUT.getPersons().collect { result ->
//                assertEquals(result.size, 101_640)
                assertEquals(result.size, 3)
            }
        }
    }
}