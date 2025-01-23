package com.example.myapplication.utilities

fun giveNonAmpUrl(url: String): String {
    return if (url.startsWith("www.google.com/amp/s/amp")) {
        url.replace("google.com/amp/s/amp.", "")
    } else {
        url
    }
}