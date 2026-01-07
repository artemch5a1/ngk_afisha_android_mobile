package com.example.ngkafisha.domain.common.exceptions

open class ApiException(
    val code: Int,
    message: String
) : Exception(message)