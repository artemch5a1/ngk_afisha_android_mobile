package com.example.application.identityService.accountContext.useCases

import com.example.domain.common.models.CustomResult
import com.example.domain.identityService.accountContext.abstractions.service.auth.SessionStoreService
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class LogoutUseCaseTest {

    private lateinit var logoutUseCase: LogoutUseCase
    private lateinit var sessionStoreService: SessionStoreService

    @Before
    fun setup() {
        sessionStoreService = mockk(relaxed = true)
        logoutUseCase = LogoutUseCase(sessionStoreService)
    }

    @Test
    fun `invoke should clear all session data`() = runTest {
        // Given
        coEvery { sessionStoreService.clearAllSessionData() } returns Unit

        // When
        val result = logoutUseCase(Unit)

        // Then
        assertTrue(result.isSuccess)
        coVerify { sessionStoreService.clearAllSessionData() }
    }

    @Test
    fun `invoke should return success result`() = runTest {
        // When
        val result = logoutUseCase(Unit)

        // Then
        assertTrue(result.isSuccess)
    }
}
