/*
 * Copyright (c) 2021. Cognizant Softvision
 * Krisztian Bokor
 *
 */

package com.softvision.krisztianbokor.presentation.ui.person

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.softvision.krisztianbokor.R
import kotlinx.android.synthetic.main.person_fragment.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

@ExperimentalCoroutinesApi
class PersonFragment : Fragment() {

    private val viewModel: PersonViewModel by viewModel()

    private val personAdapter: PersonAdapter by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.person_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        persons_recycler_view.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = personAdapter
        }
        viewModel.uiState.observe(viewLifecycleOwner, Observer { handleUiState(it) })
        viewModel.uiEvent.observe(viewLifecycleOwner, Observer { handleUiEvent(it) })
    }

    private fun handleUiState(uiState: PersonUiState) =
        uiState.apply {
            when (dataStatus) {
                PersonUiDataStatus.Loading -> showLoading()

                PersonUiDataStatus.Loaded -> hideLoading()

                PersonUiDataStatus.Data -> personAdapter.submitList(data)
            }
        }

    private fun handleUiEvent(uiEvent: PersonUiEvent) =
        when (uiEvent) {
            is PersonUiEvent.Error -> handleError(uiEvent.message)
        }

    private fun handleError(message: String) {
        hideLoading()
        showErrorMessage(message)
        error_message_view.visibility = View.VISIBLE
    }

    private fun showLoading() {
        loading_view.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        loading_view.visibility = View.GONE
    }

    private fun showErrorMessage(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}
