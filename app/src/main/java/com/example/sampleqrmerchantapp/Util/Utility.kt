package com.example.sampleqrmerchantapp.Util

import android.content.Context
import android.content.SharedPreferences

object Utility {
    fun saveMID(mid: String?, context: Context) {
        val pref : SharedPreferences = context.getSharedPreferences("APP_PREFERENCES", Context.MODE_PRIVATE)
        val editor = pref.edit()
        val existingMIDs = pref.getStringSet("MIDs", HashSet())?.toMutableSet() ?: mutableSetOf()

        if(!existingMIDs.contains(mid)) {
            existingMIDs.add(mid)
        }

        editor.putStringSet("MIDs", existingMIDs)
        editor.apply()
    }
}