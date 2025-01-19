package com.example.myapplication.utilities

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray


val client = OkHttpClient()

suspend fun extractSearchSuggestion(searchString: String): List<String> {
    val url = "https://www.google.co.uk/complete/search?q=$searchString&cp=1&client=gws-wiz&xssi=t&gs_pcrt=undefined&hl=ne&authuser=0&psi=ChyLZ7WFMriMseMPhrGfsQ4.1737169931462&dpr=1"

    val request = Request.Builder()
        .url(url)
        .build()

    return withContext(Dispatchers.IO) {
        client.newCall(request).execute().use { response ->
            val responseString = response.body?.string()?.trim()?.removePrefix(")]}'")
            responseString?.let {
                parseJsonAndExtractTitles(it)
            } ?: emptyList()
        }
    }
}

private fun parseJsonAndExtractTitles(jsonString: String): List<String> {
    val titles = mutableListOf<String>()
    val rootArray = JSONArray(jsonString)
    val dataArray = rootArray.getJSONArray(0)
    for (i in 0 until dataArray.length()) {
        val itemArray = dataArray.getJSONArray(i)
        val title = itemArray.getString(0) // Extract the title
        titles.add(title)
    }
    return titles
}