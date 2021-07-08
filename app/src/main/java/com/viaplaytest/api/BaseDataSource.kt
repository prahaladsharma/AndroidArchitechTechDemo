package com.viaplaytest.api

import retrofit2.Response
import timber.log.Timber
import com.viaplaytest.data.Result
/**
 * Abstract Base Data source class with error handling
 */
abstract class BaseDataSource {

    protected suspend fun <T> getResult(call: suspend () -> Response<T>): Result<T> {

        try {
            val response = call()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) return Result.success(body)
            }
            return error(" ${response.code()} ${response.message()}")
        } catch (e: Exception) {
            return error(e.message ?: e.toString())
        }
    }

    private fun <T> error(message: String): Result<T> {

        /** We can deserialize error model (in case we get error msg from server)
         * and pass the message */
        Timber.e(message)
        return Result.error("Network call has failed for a following reason: $message")
    }
}

