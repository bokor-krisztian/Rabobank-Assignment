/*
 * Copyright (c) 2021. Cognizant Softvision
 * Krisztian Bokor
 *
 */

package com.softvision.krisztianbokor.data.repository

import com.softvision.krisztianbokor.data.datasource.local.PersonLocalDataSource
import com.softvision.krisztianbokor.domain.model.PersonModel
import com.softvision.krisztianbokor.domain.repository.PersonRepository
import kotlinx.coroutines.flow.Flow

class PersonRepositoryImpl(
    private val personLocalDataSource: PersonLocalDataSource
) : PersonRepository {

    override fun getPersons(): Flow<List<PersonModel>> = personLocalDataSource.getPersons()
}
