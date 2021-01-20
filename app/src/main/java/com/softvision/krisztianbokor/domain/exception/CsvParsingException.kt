/*
 * Copyright (c) 2021. Cognizant Softvision
 * Krisztian Bokor
 *
 */

package com.softvision.krisztianbokor.domain.exception

class CsvParsingException(message: String? = "Exception while parsing CSV file") :
    Exception(message)