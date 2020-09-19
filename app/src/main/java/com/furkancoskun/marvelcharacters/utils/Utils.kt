package com.furkancoskun.marvelcharacters.utils

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import androidx.core.graphics.drawable.toDrawable
import retrofit2.HttpException
import java.lang.Exception
import com.furkancoskun.marvelcharacters.utils.Status.*

enum class Status {
    SUCCESS,
    ERROR,
    LOADING,
    EMPTY
}

data class Resource<out T>(val status: Status, val data: T?, val exception: Exception?) {
    companion object {
        fun <T> success(data: T): Resource<T> =
            Resource(status = SUCCESS, data = data, exception = null)

        fun <T> error(data: T?, exception: Exception): Resource<T> =
            Resource(status = ERROR, data = data, exception = exception)

        fun <T> empty(): Resource<T> =
            Resource(status = EMPTY, data = null, exception = null)

        fun <T> loading(data: T?): Resource<T> =
            Resource(status = LOADING, data = data, exception = null)
    }
}

open class ResponseHandler {
    fun <T : Any> handleSuccess(data: T): Resource<T> {
        return Resource.success(data)
    }

    fun <T : Any> handleException(e: Exception): Resource<T> {
        return when (e) {
            is HttpException -> Resource.error(data = null, exception = e)
            is KotlinNullPointerException -> Resource.empty()
            else -> Resource.error(data = null, exception = e)
        }
    }
}
