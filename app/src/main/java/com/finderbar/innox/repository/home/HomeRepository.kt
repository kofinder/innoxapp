package com.finderbar.innox.repository.home

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import retrofit2.Response
import retrofit2.http.GET

interface HomeRepository {
    @GET("/api/users/2")
    suspend fun getPerson(): Response<ServiceResponse<Person>>
}

@Keep
data class ServiceResponse<T>(
    @SerializedName("data") val data: T
)

@Keep
data class Person(
    @SerializedName("id") val id: Long,
    @SerializedName("email") val email: String,
    @SerializedName("first_name") val firstName: String,
    @SerializedName("last_name") val lastName: String
)