/*
 * Copyright (c) 2021. Cognizant Softvision
 * Krisztian Bokor
 *
 */

package com.softvision.krisztianbokor.util

import android.content.Context
import com.softvision.krisztianbokor.data.datasource.local.PersonLocalDataSource
import com.softvision.krisztianbokor.data.repository.PersonRepositoryImpl
import com.softvision.krisztianbokor.domain.repository.PersonRepository
import com.softvision.krisztianbokor.domain.usecase.GetPersonsUseCase
import com.softvision.krisztianbokor.presentation.ui.person.PersonViewModel
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

@ExperimentalCoroutinesApi
val personTestModule = module {

    single(override = true) { mockk<Context>() }

    viewModel(override = true) { PersonViewModel(get()) }


    factory(override = true) { GetPersonsUseCase(get()) }

    factory<PersonRepository>(override = true) { PersonRepositoryImpl(get()) }

    factory(override = true) { PersonLocalDataSource(get()) }
}
