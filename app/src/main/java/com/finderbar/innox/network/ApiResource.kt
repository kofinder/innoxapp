package com.finderbar.innox.network
/**
 * Created by: finderbar
 * Created at: 09,December,2020
 */
enum class Status {
    SUCCESS,
    ERROR,
    LOADING
}

data class Resource<out T>(val status: Status, val data: T?, val code: Int?, val msg: String?) {
    companion object {

        fun <T> success(data: T?): Resource<T> = Resource(Status.SUCCESS, data, 1, null)

        fun <T> error(code: Int, msg: String, data: T? = null): Resource<T> = Resource(Status.ERROR, data, code, msg)

        fun <T> loading(data: T? = null): Resource<T> = Resource(Status.LOADING, data, 0, null)

    }
}
