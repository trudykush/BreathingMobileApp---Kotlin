package com.kush.breathingapplication.util

import android.annotation.SuppressLint
import android.app.Activity
import android.content.SharedPreferences
import java.util.*

/**
 * Created by Kush on 27/08/2020.
 */
class Prefs(activity: Activity) {

    private var preferences: SharedPreferences = activity.getPreferences(Activity.MODE_PRIVATE)

    val date: String get() {
        val milliDate = preferences.getLong("seconds", 0)
        val amOrPm: String

        val calendar = Calendar.getInstance()
        calendar.timeInMillis = milliDate

        val a = calendar.get(Calendar.AM_PM)
        amOrPm = if (a == Calendar.AM)
            "AM"
        else
            "PM"

        return ("Last " + calendar.get(Calendar.HOUR_OF_DAY) +
                ": " + calendar.get(Calendar.MINUTE) + " " + amOrPm)
    }

    fun setDate(milliseconds: Long) {
        preferences.edit().putLong("seconds", milliseconds)
            .apply()
    }

    var sessions: Int
    get() = preferences.getInt("sessions", 0)
    set(session) = preferences.edit().putInt("sessions", session).apply()

    var breathe: Int
    get() = preferences.getInt("breaths", 0)
    set(breath) = preferences.edit().putInt("breaths", breath).apply()

}