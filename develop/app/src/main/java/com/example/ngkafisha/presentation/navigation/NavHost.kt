package com.example.ngkafisha.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.domain.identityService.accountContext.abstractions.service.auth.SessionInfoStore
import com.example.ngkafisha.presentation.screens.AboutAppScreen
import com.example.ngkafisha.presentation.screens.ChangePasswordScreen
import com.example.ngkafisha.presentation.screens.CreateOrEditEventScreen
import com.example.ngkafisha.presentation.screens.CreateOrEditInvitationScreen
import com.example.ngkafisha.presentation.screens.EditPublisherProfileScreen
import com.example.ngkafisha.presentation.screens.EditStudentProfileScreen
import com.example.ngkafisha.presentation.screens.EditUserProfileScreen
import com.example.ngkafisha.presentation.screens.EventScreen
import com.example.ngkafisha.presentation.screens.InvitationScreen
import com.example.ngkafisha.presentation.screens.ListEventScreen
import com.example.ngkafisha.presentation.screens.ListInvitationScreen
import com.example.ngkafisha.presentation.screens.ProfileScreen
import com.example.ngkafisha.presentation.screens.PublisherProfileScreen
import com.example.ngkafisha.presentation.screens.RequestsScreen
import com.example.ngkafisha.presentation.screens.SignInScreen
import com.example.ngkafisha.presentation.screens.SignUpScreen
import com.example.ngkafisha.presentation.screens.SplashScreen
import com.example.ngkafisha.presentation.screens.StudentProfileScreen


@Composable
fun NavHost(
    navController: NavHostController,
    sessionInfoStore: SessionInfoStore
) {

    DisposableEffect(Unit) {
        val subscription = sessionInfoStore.sessionChanged.subscribe { state ->
            if (!state.sessionInfoStore.isAuth) {

                if(state.message != null){
                    navController.navigate("signIn/${state.message}") {
                        popUpTo(0)
                    }
                    return@subscribe
                }

                navController.navigate("signIn") {
                    popUpTo(0)
                }
            }
        }

        onDispose {
            subscription.unsubscribe()
        }
    }

    NavHost(navController = navController, startDestination = "splash") {

        composable("splash") { SplashScreen(navController) }
        composable("signIn") { SignInScreen(navController) }
        composable("signUp") { SignUpScreen(navController) }
        composable("listEventScreen") { ListEventScreen(navController) }
        composable("home") { ListEventScreen(navController) }
        composable("inv") { ListInvitationScreen(navController) }
        composable("requests") { RequestsScreen(navController) }
        composable("profile") { ProfileScreen(navController) }

        composable("studentProfile") {
            StudentProfileScreen(navController)
        }

        composable("publisherProfile") {
            PublisherProfileScreen(navController)
        }

        composable("changePassword") {
            ChangePasswordScreen(navController)
        }

        composable("editUserProfile") {
            EditUserProfileScreen(navController)
        }

        composable("editStudentProfile") {
            EditStudentProfileScreen(navController)
        }

        composable("editPublisherProfile") {
            EditPublisherProfileScreen(navController)
        }

        composable("aboutScreen") { AboutAppScreen(navController) }

        composable(
            route = "eventScreen/{eventId}",
            arguments = listOf(navArgument("eventId") { type = NavType.StringType })
        ) { backStackEntry ->
            val eventId = backStackEntry.arguments?.getString("eventId") ?: ""
            EventScreen(navController, eventId)
        }

        composable(
            route = "invitationScreen/{invitationId}",
            arguments = listOf(navArgument("invitationId") { type = NavType.StringType })
        ) { backStackEntry ->
            val invitationId = backStackEntry.arguments?.getString("invitationId") ?: ""
            InvitationScreen(navController, invitationId)
        }

        composable(
            route = "inv/{eventId}",
            arguments = listOf(navArgument("eventId") { type = NavType.StringType })
        ) { backStackEntry ->
            val eventId = backStackEntry.arguments?.getString("eventId") ?: ""
            ListInvitationScreen(navController, eventId)
        }

        composable(
            route = "createInvitation/{eventId}",
            arguments = listOf(navArgument("eventId") { type = NavType.StringType })
        ) { backStackEntry ->
            val eventId = backStackEntry.arguments?.getString("eventId") ?: ""
            CreateOrEditInvitationScreen(navController, eventId)
        }

        composable(
            route = "updateInvitation/{eventId}/{invitationId}",
            arguments = listOf(
                navArgument("eventId") { type = NavType.StringType },
                navArgument("invitationId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val eventId = backStackEntry.arguments?.getString("eventId") ?: ""

            val invitationId = backStackEntry.arguments?.getString("invitationId") ?: ""

            CreateOrEditInvitationScreen(navController, eventId, invitationId)
        }

        composable("createEventScreen") {
            CreateOrEditEventScreen(navController, eventId = null)
        }

        composable(
            "updateEventScreen/{eventId}",
            arguments = listOf(navArgument("eventId") { type = NavType.StringType })
        ) { backStackEntry ->
            val eventId = backStackEntry.arguments?.getString("eventId") ?: ""
            CreateOrEditEventScreen(navController, eventId)
        }

        composable(
            "signIn/{errorMessage}",
            arguments = listOf(navArgument("errorMessage") { type = NavType.StringType })
        ) { backStackEntry ->
            val errorMessage = backStackEntry.arguments?.getString("errorMessage") ?: ""


            SignInScreen(navController, errorMessage)
        }
    }
}