package com.pwnjeswani.locusassignment.data

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.google.gson.Gson

const val LOGS = "field_logs"


open class SharedPreferences(private val context: Context) {
    private var mInstance: SharedPreferences? = null
    private var editor: SharedPreferences.Editor? = null
    private val gson = Gson()

    fun getSharePrefs(): SharedPreferences {
        if (mInstance == null)
            mInstance = PreferenceManager.getDefaultSharedPreferences(context)
        return mInstance!!
    }

    fun getEditor(): SharedPreferences.Editor {
        if (editor == null)
            editor = mInstance?.edit()
        return editor!!
    }

    fun saveLogs(list: ArrayList<FieldData.FieldDataItem>?) {
        val saveValue = list as FieldData
        val stringText = gson.toJson(saveValue)
        getEditor().putString(LOGS, stringText).apply()
    }

    fun fetchLogs(): FieldData? {
        val text = mInstance!!.getString(LOGS, null)
        return gson.fromJson(text, FieldData::class.java)
    }

}