package com.truesparrow.sales

import java.io.InputStreamReader

object Helper {
    fun readFile(fileName: String): String {
        val inputStream = Helper::class.java.getResourceAsStream(fileName)
        if (inputStream == null) {
            println("Input stream is null for file: $fileName")
            throw IllegalArgumentException("File not found: $fileName")
        }

        val builder = StringBuilder()
        val reader = InputStreamReader(inputStream, "UTF-8")
        reader.readLines().forEach { builder.append(it) }
        return builder.toString()
    }
}