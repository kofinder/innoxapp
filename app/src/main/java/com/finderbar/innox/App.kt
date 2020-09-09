package com.finderbar.innox
import android.app.Application
import android.content.Context
import android.content.ContextWrapper
import androidx.appcompat.app.AppCompatDelegate
import androidx.multidex.MultiDex

private lateinit var INSTANCE: Application

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
    }

    override fun attachBaseContext(base: Context?) {
        MultiDex.install(base)
        super.attachBaseContext(base)
    }
}

object AppContext : ContextWrapper(INSTANCE)
