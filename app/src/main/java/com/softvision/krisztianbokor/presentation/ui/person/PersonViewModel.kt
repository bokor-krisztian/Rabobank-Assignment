/*
 * Copyright (c) 2021. Cognizant Softvision
 * Krisztian Bokor
 *
 */

package com.softvision.krisztianbokor.presentation.ui.person

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.softvision.krisztianbokor.domain.usecase.GetPersonsResult
import com.softvision.krisztianbokor.domain.usecase.GetPersonsUseCase
import com.softvision.krisztianbokor.presentation.util.SingleLiveEvent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class PersonViewModel(private val getPersonsUseCase: GetPersonsUseCase) : ViewModel() {

    private val _uiState: MutableLiveData<PersonUiState> = MutableLiveData()
    val uiState: LiveData<PersonUiState>
        get() = _uiState

    private val _uiEvent: SingleLiveEvent<PersonUiEvent> = SingleLiveEvent()
    val uiEvent: LiveData<PersonUiEvent>
        get() = _uiEvent

    init {
        getPersons()
    }

    private fun getPersons() {
        viewModelScope.launch {
            getPersonsUseCase()
                .onStart { _uiState.postValue(PersonUiState(dataStatus = PersonUiDataStatus.Loading)) }
                .collect { result ->
                    _uiState.value = PersonUiState(PersonUiDataStatus.Loaded)
                    when (result) {
                        is GetPersonsResult.HasData ->
                            _uiState.value = PersonUiState(PersonUiDataStatus.Data, result.data)

                        is GetPersonsResult.Error ->
                            _uiEvent.value = PersonUiEvent.Error(result.message)
                    }
                }
        }
    }
}