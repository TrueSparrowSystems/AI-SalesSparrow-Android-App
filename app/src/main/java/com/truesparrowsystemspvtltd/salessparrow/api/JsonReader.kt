package com.truesparrowsystemspvtltd.salessparrow.api

import android.content.res.AssetManager
import java.io.BufferedReader
import java.io.InputStream

class JsonReader(
    private val assetManager: AssetManager,
) {
    fun getJsonAsString(jsonFileName: String?): String {
        val content = StringBuilder()
        val reader = BufferedReader(getJsonInputStream(jsonFileName).reader())
        var line = reader.readLine()
        while (line != null) {
            content.append(line)
            line = reader.readLine()
        }
        return content.toString()
    }

    private fun getJsonInputStream(jsonFileName: String?): InputStream {
        val jsonFilePath = String.format("%s", jsonFileName)
        return assetManager.open(jsonFilePath)
    }
}