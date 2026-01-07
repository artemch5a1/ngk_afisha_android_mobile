package com.example.ngkafisha.presentation.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.ngkafisha.presentation.components.DatePickerField
import com.example.ngkafisha.presentation.components.DefaultField
import com.example.ngkafisha.presentation.components.StateButton
import com.example.ngkafisha.presentation.models.states.ActualState
import com.example.ngkafisha.presentation.viewmodels.EditUserProfileViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditUserProfileScreen(
    navController: NavController,
    viewModel: EditUserProfileViewModel = hiltViewModel()
) {

    LaunchedEffect(Unit) {
        viewModel.loadUser()
    }

    val state by viewModel.actualState.collectAsState()
    val userState by viewModel.userState.collectAsState()
    val user by viewModel.user.collectAsState()

    val textAction by viewModel.textAction.observeAsState("Сохранить")
    val textPage by viewModel.textPage.observeAsState("Редактирование профиля")

    if (userState is ActualState.Success) {
        LaunchedEffect(Unit) {
            navController.popBackStack()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(textPage) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Назад"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->

        when {
            state is ActualState.Loading || user == null -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(48.dp)
                    )
                }
            }

            else -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    item {

                        DefaultField(
                            text = user!!.surname,
                            onValueChange = {
                                viewModel.updateUser(user!!.copy(surname = it))
                            },
                            label = "Фамилия"
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        DefaultField(
                            text = user!!.name,
                            onValueChange = {
                                viewModel.updateUser(user!!.copy(name = it))
                            },
                            label = "Имя"
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        DefaultField(
                            text = user!!.patronymic,
                            onValueChange = {
                                viewModel.updateUser(user!!.copy(patronymic = it))
                            },
                            label = "Отчество"
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        DatePickerField(
                            label = "Дата рождения",
                            selectedDate = user!!.birthDate,
                            onDateSelected = {
                                viewModel.updateUser(user!!.copy(birthDate = it))
                            }
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        StateButton(
                            actualState = userState,
                            textButton = textAction,
                            onClick = {
                                viewModel.saveChanges()
                            },
                            buttonWidth = 250.dp
                        )

                        Spacer(modifier = Modifier.height(24.dp))
                    }
                }
            }
        }
    }
}