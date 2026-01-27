package com.example.application.common.base

import com.example.domain.common.exceptions.ApiException
import com.example.domain.common.models.CustomResult
import com.example.domain.identityService.accountContext.abstractions.service.auth.SessionStoreService
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class BaseUseCaseTest {

    private lateinit var testUseCase: TestUseCase
    private lateinit var sessionStoreService: SessionStoreService

    @Before
    fun setup() {
        sessionStoreService = mockk(relaxed = true)
        testUseCase = TestUseCase(sessionStoreService)
    }

    @Test
    fun `invoke should return success when invokeLogic succeeds`() = runTest {
        // Given
        val request = "test"
        val expectedResult = "success"

        // When
        val result = testUseCase(request)

        // Then
        assertTrue(result.isSuccess)
        assertEquals(expectedResult, result.value)
    }

    @Test
    fun `invoke should handle 401 error and reset session`() = runTest {
        // Given
        val request = "test_401"
        val apiException = ApiException(401, "Unauthorized")

        // When
        val result = testUseCase(request)

        // Then
        assertFalse(result.isSuccess)
        coVerify { sessionStoreService.resetSessionWithClear("Сессия была завершена, пожалуйста войдите заново") }
    }

    @Test
    fun `invoke should use custom error message from codeMessageMap`() = runTest {
        // Given
        val request = "test_400"
        val apiException = ApiException(400, "Bad Request")

        // When
        val result = testUseCase(request)

        // Then
        assertFalse(result.isSuccess)
        assertEquals("Custom error message", result.errorMessage)
    }

    @Test
    fun `invoke should handle generic exceptions`() = runTest {
        // Given
        val request = "test_exception"
        val exception = RuntimeException("Generic error")

        // When
        val result = testUseCase(request)

        // Then
        assertFalse(result.isSuccess)
        assertEquals("Generic error", result.errorMessage)
    }

    // Test implementation of BaseUseCase
    private class TestUseCase(
        sessionStoreService: SessionStoreService
    ) : BaseUseCase<String, String>(sessionStoreService) {

        override val codeMessageMap: Map<Int, String>
            get() = mapOf(400 to "Custom error message")

        override suspend fun invokeLogic(request: String): CustomResult<String> {
            return when (request) {
                "test" -> CustomResult.success("success")
                "test_401" -> throw ApiException(401, "Unauthorized")
                "test_400" -> throw ApiException(400, "Bad Request")
                "test_exception" -> throw RuntimeException("Generic error")
                else -> CustomResult.success("default")
            }
        }
    }
}
