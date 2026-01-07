package com.example.ngkafisha.presentation.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.*
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.ngkafisha.R
import com.example.ngkafisha.domain.common.enums.Role
import com.example.ngkafisha.domain.identityService.accountContext.abstractions.service.auth.SessionInfoStore

@Composable
fun BottomBar(
    navController: NavController,
    sessionInfoStore: SessionInfoStore,
    modifier: Modifier = Modifier
) {

    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry.value?.destination?.route

    val role = sessionInfoStore.currentAccount?.accountRole

    val invLabel = when(role) {
        Role.Publisher -> "Мои приглашения"
        else -> "Приглашения"
    }

    val reqLabel = when(role) {
        Role.Publisher -> "Заявки"
        else -> "Мои заявки"
    }

    NavigationBar(
        modifier = modifier.height(105.dp)
    ) {

        val iconSize = Modifier.size(35.dp)

        @Composable
        fun BottomNavItem(
            label: String,
            routes: List<String>,
            iconRes: Int,
            selectedAdditional:(currentRoute: String?) -> Boolean = {false}
        ) {
            val isSelected = routes.contains(currentRoute) || selectedAdditional(currentRoute)

            NavigationBarItem(
                selected = isSelected,
                enabled = !isSelected,
                onClick = {
                    if (!isSelected) {
                        navController.navigate(routes.first())
                    }
                },
                icon = {
                    Icon(
                        painterResource(iconRes),
                        contentDescription = label,
                        modifier = iconSize
                    )
                },
                label = {
                    Column(modifier = Modifier.height(30.dp)) {
                        Text(
                            text = label,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            maxLines = 2
                        )
                    }
                }
            )
        }

        BottomNavItem("Главная", listOf("home", "listEventScreen"), R.drawable.home)

        BottomNavItem(invLabel, listOf("inv"), R.drawable.inv, { currentRoute ->

            if(currentRoute == null)
                return@BottomNavItem false

            return@BottomNavItem currentRoute.startsWith("inv/")
        })

        BottomNavItem(reqLabel, listOf("requests"), R.drawable.requests)
        BottomNavItem("Профиль", listOf("profile"), R.drawable.profile)
    }
}
