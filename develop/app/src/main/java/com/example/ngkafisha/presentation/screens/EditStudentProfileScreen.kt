package com.example.ngkafisha.presentation.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.ngkafisha.presentation.components.GroupDropdownField
import com.example.ngkafisha.presentation.components.StateButton
import com.example.ngkafisha.presentation.models.states.ActualState
import com.example.ngkafisha.presentation.viewmodels.EditStudentProfileViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditStudentProfileScreen(
    navController: NavController,
    viewModel: EditStudentProfileViewModel = hiltViewModel()
) {

    LaunchedEffect(Unit) {
        viewModel.loadData()
    }

    val state by viewModel.actualState.collectAsState()
    val studentState by viewModel.studentState.collectAsState()
    val student by viewModel.student.collectAsState()
    val groups by viewModel.groups.collectAsState()

    if (studentState is ActualState.Success) {
        LaunchedEffect(Unit) {
            navController.popBackStack()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Редактирование профиля студента"
                    )
                },
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
            state is ActualState.Loading || student == null -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            state is ActualState.Error -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    Text((state as ActualState.Error).message)
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

                        Text(
                            text = "Группа",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(Modifier.height(20.dp))

                        GroupDropdownField(
                            label = "Группа",
                            groups = groups,
                            selectedGroup = groups.firstOrNull {
                                it.groupId == student!!.groupId
                            },
                            onGroupSelected = { group ->
                                viewModel.updateGroup(group)
                            }
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        StateButton(
                            actualState = studentState,
                            textButton = "Сохранить",
                            onClick = {
                                viewModel.saveChanges()
                            },
                            buttonWidth = 250.dp
                        )
                    }
                }
            }
        }
    }
}
