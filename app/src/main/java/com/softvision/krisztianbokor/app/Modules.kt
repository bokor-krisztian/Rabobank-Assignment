/*
 * Copyright (c) 2021. Cognizant Softvision
 * Krisztian Bokor
 *
 */

package com.softvision.krisztianbokor.app

import com.softvision.krisztianbokor.data.datasource.local.PersonLocalDataSource
import com.softvision.krisztianbokor.data.repository.PersonRepositoryImpl
import com.softvision.krisztianbokor.domain.repository.PersonRepository
import com.softvision.krisztianbokor.domain.usecase.GetPersonsUseCase
import com.softvision.krisztianbokor.presentation.ui.person.PersonAdapter
import com.softvision.krisztianbokor.presentation.ui.person.PersonViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

@ExperimentalCoroutinesApi
val personModule = module {

    viewModel { PersonViewModel(get()) }

    factory { GetPersonsUseCase(get()) }

    factory<PersonRepository> { PersonRepositoryImpl(get()) }

    factory { PersonLocalDataSource(get()) }

    factory { PersonAdapter() }
}