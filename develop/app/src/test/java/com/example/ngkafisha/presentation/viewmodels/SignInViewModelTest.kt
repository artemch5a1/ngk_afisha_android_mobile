package com.example.ngkafisha.presentation.viewmodels

import androidx.navigation.NavHostController
import com.example.application.identityService.accountContext.useCases.LoginUseCase
import com.example.domain.common.enums.Role
import com.example.domain.identityService.accountContext.abstractions.service.auth.SessionInfoStore
import com.example.domain.identityService.accountContext.models.Account
import com.example.domain.identityService.accountContext.models.AccountSession
import com.example.ngkafisha.presentation.models.states.ActualState
import com.example.ngkafisha.presentation.models.states.SignInState
import com.example.ngkafisha.presentation.settings.SessionRepository
import io.mockk.coEvery
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
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.util.UUID

@OptIn(ExperimentalCoroutinesApi::class)
class SignInViewModelTest {

    private lateinit var viewModel: SignInViewModel
    private lateinit var loginUseCase: LoginUseCase
    private lateinit var sessionInfoStore: SessionInfoStore
    private lateinit var sessionRepository: SessionRepository
    private lateinit var navController: NavHostController

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        
        loginUseCase = mockk()
        sessionInfoStore = mockk(relaxed = true)
        sessionRepository = mockk(relaxed = true)
        navController = mockk(relaxed = true)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `init should load saved email and password from repository`() = runTest(testDispatcher) {
        // Given
        val savedEmail = "test@example.com"
        val savedPassword = "password123"
        
        coEvery { sessionRepository.getSavedEmail() } returns savedEmail
        coEvery { sessionRepository.getSavedPassword() } returns savedPassword

        // When
        viewModel = SignInViewModel(loginUseCase, sessionInfoStore, sessionRepository)
        advanceUntilIdle()

        // Then
        assertEquals(savedEmail, viewModel.signInState.email)
        assertEquals(savedPassword, viewModel.signInState.password)
    }

    @Test
    fun `init should use empty strings when no saved credentials`() = runTest(testDispatcher) {
        // Given
        coEvery { sessionRepository.getSavedEmail() } returns null
        coEvery { sessionRepository.getSavedPassword() } returns null

        // When
        viewModel = SignInViewModel(loginUseCase, sessionInfoStore, sessionRepository)
        advanceUntilIdle()

        // Then
        assertEquals("", viewModel.signInState.email)
        assertEquals("", viewModel.signInState.password)
    }

    @Test
    fun `updateSign should update sign in state`() = runTest(testDispatcher) {
        // Given
        coEvery { sessionRepository.getSavedEmail() } returns null
        coEvery { sessionRepository.getSavedPassword() } returns null

        viewModel = SignInViewModel(loginUseCase, sessionInfoStore, sessionRepository)
        advanceUntilIdle()

        // When
        val newState = SignInState(email = "new@example.com", password = "newpass")
        viewModel.updateSign(newState)

        // Then
        assertEquals("new@example.com", viewModel.signInState.email)
        assertEquals("newpass", viewModel.signInState.password)
    }

    @Test
    fun `setErrorState should set error state`() = runTest(testDispatcher) {
        // Given
        coEvery { sessionRepository.getSavedEmail() } returns null
        coEvery { sessionRepository.getSavedPassword() } returns null

        viewModel = SignInViewModel(loginUseCase, sessionInfoStore, sessionRepository)
        advanceUntilIdle()

        // When
        viewModel.setErrorState("Test error")

        // Then
        val state = viewModel.actualState.first()
        assertTrue(state is ActualState.Error)
        if (state is ActualState.Error) {
            assertEquals("Test error", state.message)
        }
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
