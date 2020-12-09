package com.finderbar.innox

import android.content.Context
import android.content.SharedPreferences
/**
 * Created by: finderbar
 * Created at: 09,December,2020
 */
class Prefs(context: Context) {

    companion object {
        private const val PREFS_FILENAME = "com.finderbar.innox.prefs"
        private const val USER_ID = "userId";
        private const val USER_NAME = "userName"
        private const val USER_PHONE = "phone"
        private const val USER_AVATAR = "avatar"
        private const val USER_TOKEN = "authToken"
        private const val SHOPPING_COUNT="cartCount"
        private const val SHOPPING_KEYS="cartIds"
    }

    private val prefs: SharedPreferences = context.getSharedPreferences(PREFS_FILENAME, 0)
    private var editor = prefs.edit()

    var userId: String
        get() = prefs.getString(USER_ID, "").toString()
        set(value) = prefs.edit().putString(USER_ID, value).apply()

    var authToken: String
        get() = prefs.getString(USER_TOKEN, "").toString()
        set(value) = prefs.edit().putString(USER_TOKEN, value).apply()

    var userName: String
        get() = prefs.getString(USER_NAME, "").toString()
        set(value) = prefs.edit().putString(USER_NAME, value).apply()

    var userPhone: String
        get() = prefs.getString(USER_PHONE, "").toString()
        set(value) = prefs.edit().putString(USER_PHONE, value).apply()

    var userAvatar: String
        get() = prefs.getString(USER_AVATAR, "").toString()
        set(value) = prefs.edit().putString(USER_AVATAR, value).apply()

    var shoppingCount: Int
        get() = prefs.getInt(SHOPPING_COUNT, 0)
        set(value) = prefs.edit().putInt(SHOPPING_COUNT, value).apply()

    var cartIds: MutableSet<String>?
        get() = prefs.getStringSet(SHOPPING_KEYS, mutableSetOf())
        set(value) = prefs.edit().putStringSet(SHOPPING_KEYS, value).apply()

    fun logout() {
        editor.clear()
        editor.commit()
        editor.apply()
    }

}