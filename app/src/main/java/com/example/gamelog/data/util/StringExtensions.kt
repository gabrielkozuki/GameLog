package com.example.gamelog.data.util

import android.util.Log
import java.text.SimpleDateFormat
import java.util.Locale

fun String.formatDate(): String {
    return try {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val outputFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val date = inputFormat.parse(this)

        date?.let { outputFormat.format(it) } ?: this

    } catch (e: Exception) {
        Log.d("StringExtensions", "Error formatting date: $e")
        this
    }
}