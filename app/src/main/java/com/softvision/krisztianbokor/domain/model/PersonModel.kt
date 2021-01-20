/*
 * Copyright (c) 2021. Cognizant Softvision
 * Krisztian Bokor
 *
 */

package com.softvision.krisztianbokor.domain.model

data class PersonModel(
    val firstName: String,
    val lastName: String,
    val issueCount: Int,
    val dateOfBirth: String
) {
    companion object {
        const val FIRST_NAME = "First name"
        const val LAST_NAME = "Surname"
        const val ISSUE_COUNT = "Issue count"
        const val DATE_OF_BIRTH = "Date of birth"

        fun getDefault() = PersonModel(FIRST_NAME, LAST_NAME, 0, DATE_OF_BIRTH)
    }
}
