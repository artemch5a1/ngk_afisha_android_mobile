package com.example.ngkafisha.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.ngkafisha.presentation.components.DefaultField
import com.example.ngkafisha.presentation.components.PasswordField
import com.example.ngkafisha.presentation.components.StateButton
import com.example.ngkafisha.presentation.models.states.ActualState
import com.example.ngkafisha.presentation.viewmodels.ChangePasswordViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChangePasswordScreen(
    navController: NavController,
    viewModel: ChangePasswordViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    var oldPassword by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var repeatPassword by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Смена пароля") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Назад")
                    }
                }
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .padding(24.dp)
                .fillMaxWidth()
        ) {

            PasswordField(
                text = oldPassword,
                onValueChange = { oldPassword = it },
                label = "Старый пароль",
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(16.dp))

            PasswordField(
                text = newPassword,
                onValueChange = { newPassword = it },
                label = "Новый пароль",
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(16.dp))

            PasswordField(
                text = repeatPassword,
                onValueChange = { repeatPassword = it },
                label = "Повторите новый пароль",
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(24.dp))

            StateButton(
                textButton = "Сменить пароль",
                onClick = {
                    viewModel.changePassword(oldPassword, newPassword, repeatPassword)
                },
                actualState = state,
                buttonModifier = Modifier.fillMaxWidth(),
                circularProgressModifier = Modifier.align(Alignment.CenterHorizontally)
                    .padding(start = 150.dp),
                textModifier = Modifier.align(Alignment.CenterHorizontally),
                onSuccess = { navController.popBackStack()} )
        }
    }
}