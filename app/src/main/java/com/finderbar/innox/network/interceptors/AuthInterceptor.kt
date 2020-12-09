package com.finderbar.innox.network.interceptors
import com.finderbar.innox.Prefs
import com.finderbar.innox.prefs
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
/**
 * Created by: finderbar
 * Created at: 09,December,2020
 */
class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val requestBuilder: Request.Builder = original.newBuilder()
            .addHeader("Accept", "application/json")
            .addHeader("Content-Type", "application/json")
            .addHeader("Authorization", "Bearer ${prefs.authToken}")
            .addHeader("customer_id", prefs.userId)
        val request = requestBuilder.build()
        return chain.proceed(request)
    }
}
