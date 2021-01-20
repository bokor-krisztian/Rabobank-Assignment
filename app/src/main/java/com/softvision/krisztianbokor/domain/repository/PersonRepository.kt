/*
 * Copyright (c) 2021. Cognizant Softvision
 * Krisztian Bokor
 *
 */

package com.softvision.krisztianbokor.domain.repository

import com.softvision.krisztianbokor.domain.model.PersonModel
import kotlinx.coroutines.flow.Flow

interface PersonRepository {

    fun getPersons(): Flow<List<PersonModel>>
}
