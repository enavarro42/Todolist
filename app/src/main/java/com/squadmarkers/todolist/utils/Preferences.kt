package com.squadmarkers.todolist.utils

import android.content.Context
import android.content.SharedPreferences
import com.squadmarkers.todolist.R

class Preferences (context: Context) {

    companion object {
        private const val USER_KEY = "current_user"
    }

    private val preferences: SharedPreferences = context.getSharedPreferences(
        context.getString(R.string.prefs_file),
        Context.MODE_PRIVATE)

    var currentUser : String
        get() = preferences.getString(USER_KEY, "").orEmpty()
        set(value) = preferences.edit().putString(USER_KEY, value).apply()
}