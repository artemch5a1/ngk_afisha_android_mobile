package com.example.ngkafisha.data.common.apiMethods

import com.example.domain.common.exceptions.ApiErrorMessages.getMessage
import com.example.domain.common.exceptions.ApiException
import org.json.JSONObject
import retrofit2.HttpException
import java.net.SocketTimeoutException

suspend fun <T> safeApiCall(call: suspend () -> T): T {
    return try {
        call()
    } catch (ex: HttpException) {

        handleHttpException(ex)
    }
    catch (ex: SocketTimeoutException) {
        throw ApiException(
            -1,
            "Сервер недоступен. Проверьте подключение к интернету и повторите снова"
        )
    }
    catch (ex: Exception) {
        throw ApiException(-1, ex.message ?: "Неизвестная ошибка")
    }
}

fun handleHttpException(ex: HttpException): Nothing {
    val statusCode = ex.code()
    val errorBody = ex.response()?.errorBody()?.string()

    val serverMessage = try {
        if (!errorBody.isNullOrEmpty()) {
            val json = JSONObject(errorBody)
            json.optString("message", errorBody)
        } else null
    } catch (e: Exception) {
        errorBody
    }?.cleanMessage()

    throw ApiException(statusCode, serverMessage ?: getMessage(statusCode))
}

private fun String.cleanMessage(): String {
    return this
        .replace("[\\[\\]\"]".toRegex(), "")
        .trim()
}