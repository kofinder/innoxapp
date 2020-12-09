package com.finderbar.innox
import android.app.Application
import android.content.Context
import android.content.ContextWrapper
import androidx.appcompat.app.AppCompatDelegate
import androidx.multidex.MultiDex
/**
 * Created by: finderbar
 * Created at: 09,December,2020
 */
private lateinit var INSTANCE: Application

val prefs: Prefs by lazy {
    App.prefs!!
}

class App : Application() {

    companion object {
        var prefs: Prefs? = null
    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
        prefs = Prefs(this)
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
    }

    override fun attachBaseContext(base: Context?) {
        MultiDex.install(base)
        super.attachBaseContext(base)
    }
}

object AppContext : ContextWrapper(INSTANCE)
