package com.example.myapplication.dataClass

import android.graphics.Bitmap

data class TabInfo(
    val title: String,
    val favIcon: Bitmap?,
    val incognito: Boolean,
    val progress: Int,
    val url: String?
)