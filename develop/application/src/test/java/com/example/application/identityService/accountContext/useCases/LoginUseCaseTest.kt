package com.example.application.identityService.accountContext.useCases

import com.example.domain.common.enums.Role
import com.example.domain.common.exceptions.ApiException
import com.example.domain.identityService.accountContext.abstractions.repositories.AccountRepository
import com.example.domain.identityService.accountContext.abstractions.service.auth.SessionStoreService
import com.example.domain.identityService.accountContext.models.Account
import com.example.domain.identityService.accountContext.models.AccountSession
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.util.Locale
import java.util.UUID

class LoginUseCaseTest {

    private lateinit var loginUseCase: LoginUseCase
    private lateinit var accountRepository: AccountRepository
    private lateinit var sessionStoreService: SessionStoreService

    @Before
    fun setup() {
        accountRepository = mockk()
        sessionStoreService = mockk(relaxed = true)
        loginUseCase = LoginUseCase(accountRepository, sessionStoreService)
    }

    @Test
    fun `invoke should return success with account session on valid credentials`() = runTest {
        // Given
        val email = "test@example.com"
        val password = "password123"
        val accountSession = createAccountSession()
        
        coEvery { 
            accountRepository.takeLoginRequest(
                email.toLowerCase(Locale.ROOT), 
                password
            ) 
        } returns accountSession

        // When
        val result = loginUseCase(LoginUseCase.Request(email, password))

        // Then
        assertTrue(result.isSuccess)
        assertNotNull(result.value)
        assertEquals(accountSession, result.value)
        coVerify { sessionStoreService.setSession(accountSession) }
    }

    @Test
    fun `invoke should convert email to lowercase`() = runTest {
        // Given
        val email = "TEST@EXAMPLE.COM"
        val password = "password123"
        val accountSession = createAccountSession()
        
        coEvery { 
            accountRepository.takeLoginRequest(
                email.toLowerCase(Locale.ROOT), 
                password
            ) 
        } returns accountSession

        // When
        val result = loginUseCase(LoginUseCase.Request(email, password))

        // Then
        assertTrue(result.isSuccess)
        coVerify { 
            accountRepository.takeLoginRequest("test@example.com", password) 
        }
    }

    @Test
    fun `invoke should return failure with custom message on 400 error`() = runTest {
        // Given
        val email = "test@example.com"
        val password = "wrongpassword"
        val apiException = ApiException(400, "Invalid credentials")
        
        coEvery { 
            accountRepository.takeLoginRequest(
                email.toLowerCase(Locale.ROOT), 
                password
            ) 
        } throws apiException

        // When
        val result = loginUseCase(LoginUseCase.Request(email, password))

        // Then
        assertFalse(result.isSuccess)
        assertEquals("Неверный логин или пароль", result.errorMessage)
        coVerify(exactly = 0) { sessionStoreService.setSession(any()) }
    }

    @Test
    fun `invoke should handle generic exceptions`() = runTest {
        // Given
        val email = "test@example.com"
        val password = "password123"
        val exception = RuntimeException("Network error")
        
        coEvery { 
            accountRepository.takeLoginRequest(
                email.toLowerCase(Locale.ROOT), 
                password
            ) 
        } throws exception

        // When
        val result = loginUseCase(LoginUseCase.Request(email, password))

        // Then
        assertFalse(result.isSuccess)
        assertEquals("Network error", result.errorMessage)
    }

    private fun createAccountSession(): AccountSession {
        val account = Account(
            accountId = UUID.randomUUID(),
            email = "test@example.com",
            accountRole = Role.User
        )
        return AccountSession(
            account = account,
            accessToken = "test_token"
        )
    }
}
