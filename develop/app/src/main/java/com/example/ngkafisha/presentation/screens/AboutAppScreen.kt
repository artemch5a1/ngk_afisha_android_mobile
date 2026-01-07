package com.example.ngkafisha.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutAppScreen(navController: NavController) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("О приложении") },
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
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {

            Text(
                text = "🎭 НГК Афиша",
                style = MaterialTheme.typography.headlineMedium,
            )
            Spacer(Modifier.height(8.dp))

            Text(
                text = "НГК Афиша — приложение для студентов и сотрудников НГК. Оно помогает узнавать о мероприятиях колледжа и участвовать в них.",
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(Modifier.height(24.dp))
            Text(
                text = "Что вы можете делать:",
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(Modifier.height(8.dp))

            Text(
                text =
                    "• просматривать предстоящие события;\n" +
                            "• смотреть подробности мероприятия;\n" +
                            "• получать и принимать приглашения;\n" +
                            "• отслеживать свою активность;",
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(Modifier.height(24.dp))
            Text(
                text = "Для организаторов:",
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(Modifier.height(8.dp))

            Text(
                text =
                    "• создавать собственные события;\n" +
                            "• приглашать участников;\n" +
                            "• управлять откликами;\n" +
                            "• редактировать и обновлять мероприятия.",
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(Modifier.height(24.dp))
            Text(
                text = "🎯 Цель приложения",
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(Modifier.height(8.dp))

            Text(
                text =
                    "Приложение создано для того, чтобы упростить участие в жизни колледжа. " +
                            "Вся информация о событиях теперь в одном месте — удобно, быстро и всегда под рукой.",
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(Modifier.height(24.dp))
            Text(
                text = "📌 Статус разработки",
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(Modifier.height(8.dp))

            Text(
                text =
                    "НГК Афиша активно развивается. В будущих обновлениях появятся новые функции и улучшения.",
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(Modifier.height(32.dp))
        }
    }
}