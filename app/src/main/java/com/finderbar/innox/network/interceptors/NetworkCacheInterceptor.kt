package com.finderbar.innox.network.interceptors
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Response
import java.lang.Exception
import java.util.concurrent.TimeUnit
/**
 * Created by: finderbar
 * Created at: 09,December,2020
 */
class NetworkCacheInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val response = chain.proceed(chain.request())

        var cacheControl = CacheControl.Builder()
            .maxAge(1, TimeUnit.MINUTES)
            .build()

        response.newBuilder()
            .header("Cache-Control", cacheControl.toString())
            .build()

        request = request.newBuilder().cacheControl(cacheControl).build();

        return chain.proceed(request)
    }
}
