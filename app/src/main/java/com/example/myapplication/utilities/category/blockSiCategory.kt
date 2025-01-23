package com.example.myapplication.utilities.category

import android.util.Log
import com.example.myapplication.dataClass.BlocksiCategory
import com.example.myapplication.utilities.client
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.Request
import org.json.JSONObject
import java.net.URI

suspend fun getBlocksiCategory(url: String): List<BlocksiCategory> {
    val host = URI(url).host
    Log.d("host", "blocksi : The host of the domain is $host")
    val request = Request.Builder()
        .url("http://service2.block.si/getRating.json?url=$host")
        .build()

    return withContext(Dispatchers.IO) {
        client.newCall(request).execute().use { response ->
            val responseString = response.body?.string()

            responseString?.let {
                val x = listOf(parseJsonAndExtractTitles(it))
                Log.d("host", "blocksi response $url $x")
                x
            }
            emptyList()
        }
    }
}

private fun parseJsonAndExtractTitles(jsonString: String): BlocksiCategory? {
    val rootArray = JSONObject(jsonString)
    val dataArray = rootArray.getInt("Category")
    return BlocksiCategory.fromValue(dataArray)
}