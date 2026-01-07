package com.example.ngkafisha.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.ngkafisha.domain.identityService.accountContext.abstractions.service.auth.SessionInfoStore
import com.example.ngkafisha.presentation.navigation.BottomBar
import com.example.ngkafisha.presentation.navigation.NavHost
import com.example.ngkafisha.presentation.ui.theme.NgkafishaTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var session: SessionInfoStore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NgkafishaTheme {
                val navController = rememberNavController()

                val navBackStackEntry = navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry.value?.destination?.route

                // Маршруты, где BottomBar НЕ показывается
                val noBottomBarRoutes = listOf(
                    "splash",
                    "signIn",
                    "signUp",
                    "createEventScreen",
                    "studentProfile",
                    "aboutScreen",
                    "publisherProfile",
                    "changePassword",
                    "editUserProfile",
                    "editStudentProfile",
                    "editPublisherProfile"
                )

                val hideForSpecificScreens =
                    currentRoute?.startsWith("eventScreen/") == true ||
                            currentRoute?.startsWith("updateEventScreen/") == true ||
                            currentRoute?.startsWith("signIn/") == true ||
                            currentRoute?.startsWith("invitationScreen/") == true ||
                            currentRoute?.startsWith("createInvitation/") == true ||
                            currentRoute?.startsWith("updateInvitation/") == true

                val shouldShowBottomBar = currentRoute != null &&
                        currentRoute !in noBottomBarRoutes &&
                        !hideForSpecificScreens

                Scaffold(
                    bottomBar = {
                        if (shouldShowBottomBar) {
                            BottomBar(
                                navController,
                                session)
                        }
                    }
                ) { paddingValues ->
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        NavHost(navController, session)
                    }
                }
            }
        }
    }
}