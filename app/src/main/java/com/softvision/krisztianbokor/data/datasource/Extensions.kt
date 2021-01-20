/*
 * Copyright (c) 2021. Cognizant Softvision
 * Krisztian Bokor
 *
 */

package com.softvision.krisztianbokor.data.datasource

fun String.splitAndTrimItems(): List<String> =
    this.split(",").map { it.replace("\"", "") }

fun List<String>.getIndexOf(indexName : String) =
    this.indexOfFirst { it.equals(indexName, ignoreCase = true) }