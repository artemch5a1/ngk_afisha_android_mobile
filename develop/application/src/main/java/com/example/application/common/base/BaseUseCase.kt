package com.example.application.common.base

import com.example.domain.common.exceptions.ApiException
import com.example.domain.common.models.CustomResult
import com.example.domain.identityService.accountContext.abstractions.service.auth.SessionInfoStore
import com.example.domain.identityService.accountContext.abstractions.service.auth.SessionStoreService

abstract class BaseUseCase<TRequest, TResponse>(protected val sessionStoreService: SessionStoreService) {

    protected open val codeMessageMap: Map<Int, String> = emptyMap()

    suspend operator fun invoke(request: TRequest): CustomResult<TResponse> {
        return try {
            val result = invokeLogic(request)
            return result
        }
        catch (ex: ApiException) {

            if(ex.code == 401){
                sessionStoreService.resetSessionWithClear("Сессия была завершена, пожалуйста войдите заново")
            }

            CustomResult.failure(ex, codeMessageMap)
        }
        catch (ex: Exception) {
            CustomResult.failure(ex, codeMessageMap)
        }
    }

    protected abstract suspend fun invokeLogic(
        request: TRequest,
    ): CustomResult<TResponse>
}