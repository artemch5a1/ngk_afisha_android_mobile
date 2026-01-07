package com.example.ngkafisha.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.ngkafisha.R
import com.example.ngkafisha.presentation.components.DropdownField
import com.example.ngkafisha.presentation.components.StateButton
import com.example.ngkafisha.presentation.models.states.ActualState
import com.example.ngkafisha.presentation.viewmodels.EditPublisherProfileViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditPublisherProfileScreen(
    navController: NavController,
    viewModel: EditPublisherProfileViewModel = hiltViewModel()
) {

    LaunchedEffect(Unit) {
        viewModel.loadData()
    }

    val state by viewModel.actualState.collectAsState()
    val publisherState by viewModel.publisherState.collectAsState()
    val publisher by viewModel.publisher.collectAsState()
    val posts by viewModel.posts.collectAsState()

    if (state is ActualState.Loading || publisher == null) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        return
    }

    if (publisherState is ActualState.Success) {
        navController.popBackStack()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Редактирование профиля") },
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
    ) { padding ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {

                Text(
                    text = "Должность",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(Modifier.height(20.dp))

                DropdownField(
                    label = "Выберите должность",
                    items = posts,
                    displayMember = {it -> it?.title ?: "" },
                    selectedItem = publisher!!.post,
                    onItemSelected = { post ->
                        viewModel.updatePost(post)
                    }
                )

                Spacer(Modifier.height(32.dp))

                StateButton(
                    actualState = publisherState,
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