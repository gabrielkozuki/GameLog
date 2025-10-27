package com.example.gamelog.model

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class LocalData(context: Context) {
    private val preferences: SharedPreferences?
            = context.getSharedPreferences("localData", Context.MODE_PRIVATE)

    fun save(key: String, value: String) {
        preferences?.let {
            it.edit { putString(key, value) }
        }
    }

    fun delete(key: String) {
        preferences?.let {
            it.edit { remove(key) }
        }
    }

    fun getKey(key: String): String {
        preferences?.let {
            return it.getString(key, "") ?: ""
        }
        return ""
    }
}