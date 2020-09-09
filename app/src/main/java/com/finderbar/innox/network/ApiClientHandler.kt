package com.finderbar.innox.network

import com.finderbar.innox.AppContext
import com.finderbar.innox.network.interceptors.*
import com.google.gson.GsonBuilder
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit


object ApiClientHandler  {

    private const val BASE_URL : String = "http://188.166.207.190/innox/api/v1.0/"
    private val okHttpClient by lazy { OkHttpClient() }

    private val retrofit: Retrofit by lazy {
        val builder = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
        val dispatcher = Dispatcher()
        dispatcher.maxRequests = 1

        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val httpCacheDirectory = File(AppContext.cacheDir, "offlineCache")
        val cacheSize = 10 * 1024 * 1024 // 10 MB
        val cache = Cache(httpCacheDirectory, cacheSize.toLong())

        val client: OkHttpClient = okHttpClient.newBuilder()
            .cache(cache)
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .dispatcher(dispatcher)
            .build()
        builder.client(client).build()
    }
    fun <T> createService(tClass: Class<T>?): T {
        return retrofit.create(tClass)
    }

}
