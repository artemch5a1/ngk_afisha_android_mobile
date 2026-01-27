package com.example.application.identityService.accountContext.useCases

import com.example.domain.common.enums.Role
import com.example.domain.common.exceptions.ApiException
import com.example.domain.identityService.accountContext.abstractions.repositories.AccountRepository
import com.example.domain.identityService.accountContext.abstractions.service.auth.SessionStoreService
import com.example.domain.identityService.accountContext.models.Account
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.util.UUID

class ValidateTokenUseCaseTest {

    private lateinit var validateTokenUseCase: ValidateTokenUseCase
    private lateinit var accountRepository: AccountRepository
    private lateinit var sessionStoreService: SessionStoreService

    @Before
    fun setup() {
        accountRepository = mockk()
        sessionStoreService = mockk(relaxed = true)
        validateTokenUseCase = ValidateTokenUseCase(accountRepository, sessionStoreService)
    }

    @Test
    fun `invoke should return success with account on valid token`() = runTest {
        // Given
        val token = "valid_token"
        val account = createAccount()
        
        coEvery { accountRepository.getCurrentAccountByToken(token) } returns account

        // When
        val result = validateTokenUseCase(ValidateTokenUseCase.Request(token))

        // Then
        assertTrue(result.isSuccess)
        assertNotNull(result.value)
        assertEquals(account, result.value)
    }

    @Test
    fun `invoke should return failure on invalid token`() = runTest {
        // Given
        val token = "invalid_token"
        val apiException = ApiException(401, "Unauthorized")
        
        coEvery { accountRepository.getCurrentAccountByToken(token) } throws apiException

        // When
        val result = validateTokenUseCase(ValidateTokenUseCase.Request(token))

        // Then
        assertFalse(result.isSuccess)
        assertEquals("Unauthorized", result.errorMessage)
    }

    @Test
    fun `invoke should handle network errors`() = runTest {
        // Given
        val token = "valid_token"
        val exception = RuntimeException("Network error")
        
        coEvery { accountRepository.getCurrentAccountByToken(token) } throws exception

        // When
        val result = validateTokenUseCase(ValidateTokenUseCase.Request(token))

        // Then
        assertFalse(result.isSuccess)
        assertEquals("Network error", result.errorMessage)
    }

    private fun createAccount(): Account {
        return Account(
            accountId = UUID.randomUUID(),
            email = "test@example.com",
            accountRole = Role.User
        )
    }
}
