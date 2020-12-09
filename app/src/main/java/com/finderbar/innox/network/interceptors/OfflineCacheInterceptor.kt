package com.finderbar.innox.network.interceptors

import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Interceptor.Chain
import okhttp3.Response
import java.util.concurrent.TimeUnit

/**
 * Created by: finderbar
 * Created at: 09,December,2020
 */
class OfflineCacheInterceptor : Interceptor {
    override fun intercept(chain: Chain): Response {
                    return try {
                chain.proceed(chain.request())
            } catch (ex: Exception) {
                val cacheControl = CacheControl.Builder()
                    .onlyIfCached()
                    .maxStale(1, TimeUnit.DAYS)
                    .build()
                val offlineRequest = chain.request().newBuilder()
                    .cacheControl(cacheControl)
                    .removeHeader("Pragma")
                    .header("Cache-Control", "public, only-if-cached")
                    .build()
                chain.proceed(offlineRequest)
            }
    }
}
