package com.example.ngkafisha.presentation.screens

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.domain.identityService.userContext.models.Student
import com.example.ngkafisha.presentation.models.states.ActualState
import com.example.ngkafisha.presentation.viewmodels.StudentProfileViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentProfileScreen(
    navController: NavController,
    viewModel: StudentProfileViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val student by viewModel.student.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadStudentProfile()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Профиль студента") },
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

        when (state) {
            is ActualState.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            is ActualState.Error -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    contentAlignment = Alignment.Center
                ) {
                    Text((state as ActualState.Error).message)
                }
            }

            is ActualState.Success,
            ActualState.Initialized -> {
                student?.let {
                    StudentProfileContent(
                        student = it,
                        navController,
                        modifier = Modifier.padding(padding)
                    )
                }
            }
        }
    }
}



@Composable
fun StudentProfileContent(
    student: Student,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val user = student.user

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Box(
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
                .background(Color.LightGray),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = null,
                tint = Color.DarkGray,
                modifier = Modifier.size(90.dp)
            )
        }

        Spacer(Modifier.height(12.dp))

        Text(
            text = user.fullName(),
            style = MaterialTheme.typography.titleMedium,
            fontSize = 20.sp
        )

        Spacer(Modifier.height(32.dp))

        ProfileInfoItem("Дата рождения", user.birthDate.toString())

        Spacer(Modifier.height(24.dp))

        student.group?.let { group ->
            Divider(Modifier.padding(horizontal = 24.dp))
            Spacer(Modifier.height(16.dp))

            ProfileInfoItem("Группа", group.fullName)
            ProfileInfoItem("Курс", group.course.toString())
            ProfileInfoItem("Специальность", group.specialty.specialtyTitle)
        }

        Spacer(Modifier.height(34.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { navController.navigate("editStudentProfile") }
                .padding(horizontal = 24.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Редактировать",
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
}

@Composable
fun ProfileInfoItem(title: String, value: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 8.dp)
    ) {
        Text(
            text = title,
            color = Color.Gray,
            fontSize = 14.sp
        )
        Text(
            text = value,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium
        )
    }
}