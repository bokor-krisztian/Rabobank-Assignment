/*
 * Copyright (c) 2021. Cognizant Softvision
 * Krisztian Bokor
 *
 */

package com.softvision.krisztianbokor.domain.usecase

import com.softvision.krisztianbokor.domain.model.PersonModel
import com.softvision.krisztianbokor.domain.repository.PersonRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*

@ExperimentalCoroutinesApi
class GetPersonsUseCase(private val repository: PersonRepository) {

    suspend operator fun invoke(): Flow<GetPersonsResult> {
        return flow {
            repository.getPersons()
                .flowOn(Dispatchers.Default)
                .catch { it.message?.let { msg -> emit(GetPersonsResult.Error(msg)) } }
                .collect { emit(GetPersonsResult.HasData(it)) }
        }
    }
}

sealed class GetPersonsResult {
    class HasData(val data: List<PersonModel>) : GetPersonsResult()
    class Error(val message: String) : GetPersonsResult()
}
