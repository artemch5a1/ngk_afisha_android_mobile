package com.example.ngkafisha.presentation.viewmodels

import com.example.application.identityService.accountContext.useCases.GetCurrentAccount
import com.example.application.identityService.accountContext.useCases.LogoutUseCase
import com.example.application.identityService.userContext.useCases.userUseCases.GetCurrentUser
import com.example.domain.common.enums.Role
import com.example.domain.common.models.CustomResult
import com.example.domain.identityService.accountContext.models.Account
import com.example.domain.identityService.userContext.models.User
import com.example.ngkafisha.presentation.models.states.ActualState
import io.mockk.coEvery
import io.mockk.coVerify
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
import java.time.LocalDate
import java.util.UUID

@OptIn(ExperimentalCoroutinesApi::class)
class ProfileScreenViewModelTest {

    private lateinit var viewModel: ProfileScreenViewModel
    private lateinit var getCurrentAccount: GetCurrentAccount
    private lateinit var getCurrentUser: GetCurrentUser
    private lateinit var logoutUseCase: LogoutUseCase

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        
        getCurrentAccount = mockk()
        getCurrentUser = mockk()
        logoutUseCase = mockk(relaxed = true)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `loadProfile should set loading state initially`() = runTest(testDispatcher) {
        // Given
        val account = createAccount()
        val user = createUser()
        
        coEvery { getCurrentAccount(Unit) } returns CustomResult.success(account)
        coEvery { getCurrentUser(Unit) } returns CustomResult.success(user)

        viewModel = ProfileScreenViewModel(getCurrentAccount, getCurrentUser, logoutUseCase)

        // When
        viewModel.loadProfile()

        // Then
        val state = viewModel.state.first()
        assertTrue(state is ActualState.Loading)
    }

    @Test
    fun `loadProfile should set success state with account and user on success`() = runTest(testDispatcher) {
        // Given
        val account = createAccount()
        val user = createUser()
        
        coEvery { getCurrentAccount(Unit) } returns CustomResult.success(account)
        coEvery { getCurrentUser(Unit) } returns CustomResult.success(user)

        viewModel = ProfileScreenViewModel(getCurrentAccount, getCurrentUser, logoutUseCase)

        // When
        viewModel.loadProfile()
        advanceUntilIdle()

        // Then
        val state = viewModel.state.first()
        assertTrue(state is ActualState.Success)
        assertEquals(account, viewModel.account.first())
        assertEquals(user, viewModel.user.first())
    }

    @Test
    fun `loadProfile should set error state on account fetch failure`() = runTest(testDispatcher) {
        // Given
        val errorMessage = "Failed to load account"
        coEvery { getCurrentAccount(Unit) } returns CustomResult.failure<Account>(errorMessage)

        viewModel = ProfileScreenViewModel(getCurrentAccount, getCurrentUser, logoutUseCase)

        // When
        viewModel.loadProfile()
        advanceUntilIdle()

        // Then
        val state = viewModel.state.first()
        assertTrue(state is ActualState.Error)
        if (state is ActualState.Error) {
            assertEquals(errorMessage, state.message)
        }
    }

    @Test
    fun `loadProfile should set error state on user fetch failure`() = runTest(testDispatcher) {
        // Given
        val account = createAccount()
        val errorMessage = "Failed to load user"
        
        coEvery { getCurrentAccount(Unit) } returns CustomResult.success(account)
        coEvery { getCurrentUser(Unit) } returns CustomResult.failure<User>(errorMessage)

        viewModel = ProfileScreenViewModel(getCurrentAccount, getCurrentUser, logoutUseCase)

        // When
        viewModel.loadProfile()
        advanceUntilIdle()

        // Then
        val state = viewModel.state.first()
        assertTrue(state is ActualState.Error)
        if (state is ActualState.Error) {
            assertEquals(errorMessage, state.message)
        }
    }

    @Test
    fun `logout should call logout use case`() = runTest(testDispatcher) {
        // Given
        coEvery { getCurrentAccount(Unit) } returns CustomResult.success(createAccount())
        coEvery { getCurrentUser(Unit) } returns CustomResult.success(createUser())

        viewModel = ProfileScreenViewModel(getCurrentAccount, getCurrentUser, logoutUseCase)

        // When
        viewModel.logout()
        advanceUntilIdle()

        // Then
        coVerify { logoutUseCase(Unit) }
    }

    private fun createAccount(): Account {
        return Account(
            accountId = UUID.randomUUID(),
            email = "test@example.com",
            accountRole = Role.User
        )
    }

    private fun createUser(): User {
        return User(
            userId = UUID.randomUUID(),
            surname = "User",
            name = "Test",
            patronymic = "Testovich",
            birthDate = LocalDate.of(2000, 1, 1)
        )
    }
}
