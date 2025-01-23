package com.example.myapplication.utilities

fun giveNonAmpUrl(url: String): String {
    return if (url.contains("www.google.com/amp/s/amp")) {
        val m = url.replace("google.com/amp/s/amp.", "")
        m
    } else {
        url
    }
}