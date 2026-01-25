package com.example.ngkafisha.presentation.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.domain.common.enums.Role
import com.example.ngkafisha.presentation.models.states.ActualState
import com.example.ngkafisha.presentation.viewmodels.ProfileScreenViewModel
import com.example.ngkafisha.presentation.viewmodels.ThemeViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ProfileScreen(
    navController: NavController,
    viewModel: ProfileScreenViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val account by viewModel.account.collectAsState()
    val user by viewModel.user.collectAsState()
    val themeViewModel: ThemeViewModel = hiltViewModel()
    val isDarkTheme by themeViewModel.isDarkTheme.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadProfile()
    }

    when (state) {

        is ActualState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        is ActualState.Error -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = (state as ActualState.Error).message)
            }
        }

        is ActualState.Success,
        ActualState.Initialized -> {
            ProfileContent(
                accountEmail = account?.email ?: "",
                accountRole = account?.accountRole,
                fullName = buildString {
                    if (user != null) {
                        append(user!!.name)
                        append(" ")
                        append(user!!.surname)
                    }
                },
                onLogout = {
                    viewModel.logout()
                },
                onAbout = {
                    navController.navigate("aboutScreen")
                },
                onStudentProfile = {
                    navController.navigate("studentProfile")
                },
                onPublisherProfile = {
                    navController.navigate("publisherProfile")
                },
                onChangePassword = {
                    navController.navigate("changePassword")
                },
                onEditProfile = {
                    navController.navigate("editUserProfile")
                },
                isDarkTheme = isDarkTheme,
                onDarkThemeChange = themeViewModel::setDarkTheme
            )
        }
    }
}

@Composable
fun ProfileContent(
    accountEmail: String,
    accountRole: Role?,
    fullName: String,
    onLogout: () -> Unit,
    onAbout: () -> Unit,
    onStudentProfile: () -> Unit,
    onPublisherProfile: () -> Unit,
    onChangePassword: () -> Unit,
    onEditProfile: () -> Unit,
    isDarkTheme: Boolean,
    onDarkThemeChange: (Boolean) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 40.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // Фото профиля
        Box(
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.surfaceVariant),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = "Фото профиля",
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.size(90.dp)
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Имя
        Text(
            text = fullName.ifBlank { "Имя" },
            style = MaterialTheme.typography.titleMedium,
            fontSize = 20.sp
        )

        // Email
        Text(
            text = accountEmail,
            style = MaterialTheme.typography.bodyMedium,
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(55.dp))

        // Роль
        Text(
            text = "Ваша роль в системе: \"${accountRole?.toRussianName()}\"",
            style = MaterialTheme.typography.titleMedium,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.Start).padding(start = 24.dp)
        )

        Spacer(modifier = Modifier.height(60.dp))

        // Тема
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Тёмная тема",
                style = MaterialTheme.typography.bodyLarge,
                fontSize = 18.sp
            )
            Spacer(modifier = Modifier.weight(1f))
            Switch(
                checked = isDarkTheme,
                onCheckedChange = onDarkThemeChange
            )
        }

        // О приложении
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onAbout() }
                .padding(horizontal = 24.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "О приложении",
                style = MaterialTheme.typography.bodyLarge,
                fontSize = 18.sp
            )
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                imageVector = Icons.Default.KeyboardArrowRight,
                contentDescription = null
            )
        }

        if (accountRole == Role.User) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onStudentProfile() }
                    .padding(horizontal = 24.dp, vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Профиль студента",
                    style = MaterialTheme.typography.bodyLarge,
                    fontSize = 18.sp
                )
                Spacer(modifier = Modifier.weight(1f))
                Icon(
                    imageVector = Icons.Default.KeyboardArrowRight,
                    contentDescription = null
                )
            }
        }

        if (accountRole == Role.Publisher) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onPublisherProfile() }
                    .padding(horizontal = 24.dp, vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Профиль организатора",
                    style = MaterialTheme.typography.bodyLarge,
                    fontSize = 18.sp
                )
                Spacer(modifier = Modifier.weight(1f))
                Icon(Icons.Default.KeyboardArrowRight, contentDescription = null)
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onEditProfile() }
                .padding(horizontal = 24.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Редактировать профиль", fontSize = 18.sp)
            Spacer(Modifier.weight(1f))
            Icon(Icons.Default.KeyboardArrowRight, contentDescription = null)
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onChangePassword() }
                .padding(horizontal = 24.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Сменить пароль",
                style = MaterialTheme.typography.bodyLarge,
                fontSize = 18.sp
            )
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                imageVector = Icons.Default.KeyboardArrowRight,
                contentDescription = null
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        // Выйти из аккаунта
        Button(
            onClick = onLogout,
            modifier = Modifier
                .padding(bottom = 40.dp)
                .height(50.dp)
                .fillMaxWidth(0.8f),
            shape = RoundedCornerShape(30.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.secondaryContainer,
                contentColor = MaterialTheme.colorScheme.onSecondaryContainer
            )
        ) {
            Text(text = "Выйти из аккаунта", fontSize = 16.sp)
        }
    }
}