package com.example.ngkafisha.presentation.viewmodels

import com.example.application.identityService.accountContext.useCases.ValidateTokenUseCase
import com.example.domain.common.enums.Role
import com.example.domain.common.models.CustomResult
import com.example.domain.identityService.accountContext.abstractions.service.auth.SessionInfoStore
import com.example.domain.identityService.accountContext.models.Account
import com.example.domain.identityService.accountContext.models.AccountSession
import com.example.ngkafisha.presentation.settings.SessionRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.util.UUID

@OptIn(ExperimentalCoroutinesApi::class)
class SplashViewModelTest {

    private lateinit var viewModel: SplashViewModel
    private lateinit var sessionInfoStore: SessionInfoStore
    private lateinit var sessionRepository: SessionRepository
    private lateinit var validateTokenUseCase: ValidateTokenUseCase

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        
        sessionInfoStore = mockk(relaxed = true)
        sessionRepository = mockk(relaxed = true)
        validateTokenUseCase = mockk(relaxed = true)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `checkSession should navigate to home when token exists and session is active`() = runTest(testDispatcher) {
        // Given
        val token = "valid_token"
        every { sessionInfoStore.isAuth } returns true
        coEvery { sessionRepository.getAccessToken() } returns token

        viewModel = SplashViewModel(sessionInfoStore, sessionRepository, validateTokenUseCase)

        // When
        viewModel.checkSession()
        advanceUntilIdle()

        // Then
        assertTrue(viewModel.shouldNavigateToHome.first())
        assertFalse(viewModel.shouldNavigateToLogin.first())
        assertFalse(viewModel.isLoading.first())
    }

    @Test
    fun `checkSession should validate token and restore session when token exists but session inactive`() = runTest(testDispatcher) {
        // Given
        val token = "valid_token"
        val account = createAccount()
        val accountSession = AccountSession(account = account, accessToken = token)
        
        every { sessionInfoStore.isAuth } returns false
        coEvery { sessionRepository.getAccessToken() } returns token
        coEvery { validateTokenUseCase(ValidateTokenUseCase.Request(token)) } returns 
            CustomResult.success(account)

        viewModel = SplashViewModel(sessionInfoStore, sessionRepository, validateTokenUseCase)

        // When
        viewModel.checkSession()
        advanceUntilIdle()

        // Then
        coVerify { sessionInfoStore.setSessionWithSave(accountSession) }
        assertTrue(viewModel.shouldNavigateToHome.first())
        assertFalse(viewModel.shouldNavigateToLogin.first())
    }

    @Test
    fun `checkSession should navigate to login when token is invalid`() = runTest(testDispatcher) {
        // Given
        val token = "invalid_token"
        every { sessionInfoStore.isAuth } returns false
        coEvery { sessionRepository.getAccessToken() } returns token
        coEvery { validateTokenUseCase(ValidateTokenUseCase.Request(token)) } returns 
            CustomResult.failure<Account>("Invalid token")

        viewModel = SplashViewModel(sessionInfoStore, sessionRepository, validateTokenUseCase)

        // When
        viewModel.checkSession()
        advanceUntilIdle()

        // Then
        coVerify { sessionRepository.clearToken() }
        assertFalse(viewModel.shouldNavigateToHome.first())
        assertTrue(viewModel.shouldNavigateToLogin.first())
    }

    @Test
    fun `checkSession should navigate to login when no token exists`() = runTest(testDispatcher) {
        // Given
        coEvery { sessionRepository.getAccessToken() } returns null

        viewModel = SplashViewModel(sessionInfoStore, sessionRepository, validateTokenUseCase)

        // When
        viewModel.checkSession()
        advanceUntilIdle()

        // Then
        assertFalse(viewModel.shouldNavigateToHome.first())
        assertTrue(viewModel.shouldNavigateToLogin.first())
        assertFalse(viewModel.isLoading.first())
    }

    @Test
    fun `checkSession should set loading state initially`() = runTest(testDispatcher) {
        // Given
        coEvery { sessionRepository.getAccessToken() } returns null

        viewModel = SplashViewModel(sessionInfoStore, sessionRepository, validateTokenUseCase)

        // When
        viewModel.checkSession()

        // Then
        assertTrue(viewModel.isLoading.first())
    }

    @Test
    fun `checkSession should clear loading state after completion`() = runTest(testDispatcher) {
        // Given
        coEvery { sessionRepository.getAccessToken() } returns null

        viewModel = SplashViewModel(sessionInfoStore, sessionRepository, validateTokenUseCase)

        // When
        viewModel.checkSession()
        advanceUntilIdle()

        // Then
        assertFalse(viewModel.isLoading.first())
    }

    private fun createAccount(): Account {
        return Account(
            accountId = UUID.randomUUID(),
            email = "test@example.com",
            accountRole = Role.User
        )
    }
}
