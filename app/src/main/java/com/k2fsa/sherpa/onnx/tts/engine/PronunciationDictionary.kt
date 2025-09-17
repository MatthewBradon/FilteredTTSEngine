package com.k2fsa.sherpa.onnx.tts.engine

import android.content.Context
import android.content.SharedPreferences

class PronunciationDictionary(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("pronunciation_dict", Context.MODE_PRIVATE)

    fun getAll(): Map<String, String> {
        return prefs.all.filterValues { it is String }.mapValues { it.value as String }
    }

    fun addOrUpdate(key: String, value: String) {
        prefs.edit().putString(key, value).apply()
    }

    fun remove(key: String) {
        prefs.edit().remove(key).apply()
    }

    fun clear() {
        prefs.edit().clear().apply()
    }

    fun processText(input: String): String {
        var result = input
        for ((key, value) in getAll()) {
            // Replace whole words only, case-insensitive
            val regex = Regex("\\b" + Regex.escape(key) + "\\b", RegexOption.IGNORE_CASE)
            result = regex.replace(result, value)
        }
        return result
    }
}