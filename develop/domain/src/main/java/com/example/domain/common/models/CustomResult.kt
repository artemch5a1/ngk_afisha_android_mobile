package com.example.domain.common.models

import com.example.domain.common.exceptions.ApiException

class CustomResult<T> private constructor(
    val value: T? = null,
    val isSuccess: Boolean,
    val errorMessage: String
)
{
    companion object
    {
        fun <T> success(value: T): CustomResult<T> =
            CustomResult(value, true, "")

        fun <T> failure(message: String): CustomResult<T> =
            CustomResult(null, false, message)

        fun <T> failure(ex: Exception): CustomResult<T> = when (ex) {

            is ApiException ->
                CustomResult(null, false, ex.message ?: "")

            else ->
                CustomResult(null, false, ex.message ?: "")

        }

        fun <T> failure(ex: Exception, codeMessageMap: Map<Int, String> = emptyMap()): CustomResult<T> = when (ex) {
            is ApiException -> {
                val message = codeMessageMap[ex.code] ?: ex.message ?: ""
                CustomResult(null, false, message)
            }
            else -> CustomResult(null, false, ex.message ?: "")
        }
    }
}
