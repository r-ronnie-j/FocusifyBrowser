package com.example.myapplication.utilities.category

import android.util.Log
import com.example.myapplication.dataClass.BlocksiCategory
import com.example.myapplication.dataClass.SpinWebCategory
import com.example.myapplication.utilities.client
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.IOException
import java.net.URI

suspend fun spinCategory(url: String): List<SpinWebCategory> {
    val uri = URI(url)
    val host = uri.host
    Log.d("host", "The host of the domain is $host")

    val jsonObject = JSONObject().apply {
        put("domainName", host)
        put("key", "oz2erssx768cHfzDMOO1PsyIz2EaJsyDppqrwmHckoHsrGBOJ2tPkA==")
        put("v", "1")
    }

    val mediaType = "application/json; charset=utf-8".toMediaTypeOrNull()
    val requestBody = jsonObject.toString().toRequestBody(mediaType)

    val request = Request.Builder()
        .url("https://www.vionika.com/services/examine/domain")
        .post(requestBody)
        .build()

    return withContext(Dispatchers.IO) {
        client.newCall(request).execute().use { response ->
            val responseString = response.body?.string()
            responseString?.let {
                parseJsonAndExtractTitles(it)
            } ?: emptyList()
        }
    }
}

private fun parseJsonAndExtractTitles(jsonString: String): List<SpinWebCategory> {
    val rootObject = JSONObject(jsonString)
    val categoriesArray = rootObject.getJSONArray("categories")
    val categoriesList = mutableListOf<Int>()
    for (i in 0 until categoriesArray.length()) {
        categoriesList.add(categoriesArray.getInt(i))
    }
    return categoriesList.mapNotNull {
        SpinWebCategory.fromValue(it)
    }

}
