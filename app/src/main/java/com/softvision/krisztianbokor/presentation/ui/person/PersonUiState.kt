/*
 * Copyright (c) 2021. Cognizant Softvision
 * Krisztian Bokor
 *
 */

package com.softvision.krisztianbokor.presentation.ui.person

import com.softvision.krisztianbokor.domain.model.PersonModel

data class PersonUiState(
    val dataStatus: PersonUiDataStatus,
    val data: List<PersonModel>? = emptyList()
)

sealed class PersonUiDataStatus {
    object Loading : PersonUiDataStatus()
    object Loaded : PersonUiDataStatus()
    object Data : PersonUiDataStatus()
}

sealed class PersonUiEvent {
    class Error(val message: String) : PersonUiEvent()
}
