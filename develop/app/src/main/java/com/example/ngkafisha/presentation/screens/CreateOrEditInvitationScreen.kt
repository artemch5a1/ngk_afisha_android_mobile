package com.example.ngkafisha.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.ngkafisha.R
import com.example.ngkafisha.presentation.components.CustomTimePickerField
import com.example.ngkafisha.presentation.components.DatePickerField
import com.example.ngkafisha.presentation.components.DefaultField
import com.example.ngkafisha.presentation.components.DropdownField
import com.example.ngkafisha.presentation.components.StateButton
import com.example.ngkafisha.presentation.models.states.ActualState
import com.example.ngkafisha.presentation.viewmodels.CreateOrEditInvitationViewModel
import java.time.LocalDateTime

@Composable
fun CreateOrEditInvitationScreen(
    controlNav: NavController,
    eventId: String,
    invitationId: String? = null,
    viewModel: CreateOrEditInvitationViewModel = hiltViewModel()
) {

    LaunchedEffect(Unit) {
        viewModel.loadInvitation(eventId, invitationId)
    }

    val state by viewModel.actualState.collectAsState()
    val invitationState by viewModel.invitationState.collectAsState()
    val invitation = viewModel.invitation
    val roles by viewModel.roles.observeAsState(emptyList())
    val textAction by viewModel.textAction.observeAsState("Создать приглашение")
    val textPage by viewModel.textPage.observeAsState("Создание приглашения")

    if (state is ActualState.Loading || invitation == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        return
    }

    if (invitationState is ActualState.Success) {
        controlNav.popBackStack()
    }
    Box(modifier = Modifier.fillMaxSize().padding(16.dp),
        contentAlignment = Alignment.Center) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()

            .align(alignment = Alignment.Center),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(1) {

            Box(modifier = Modifier.fillMaxSize()) {
                IconButton(
                    onClick = {
                        controlNav.popBackStack()
                    },
                    modifier = Modifier.align(Alignment.TopStart)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_back),
                        contentDescription = "Назад",
                        tint = Color.Black
                    )
                }
            }

            Text(
                text = textPage,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 8.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            DropdownField(
                label = "Роль",
                items = roles,
                displayMember = { it?.title ?: "" },
                selectedItem = roles.firstOrNull { it.eventRoleId == invitation.roleId },
                onItemSelected = {
                    viewModel.updateInvitation(invitation.copy(roleId = it.eventRoleId))
                }
            )

            Spacer(modifier = Modifier.height(10.dp))

            DefaultField(
                text = invitation.shortDescription,
                onValueChange = {
                    viewModel.updateInvitation(invitation.copy(shortDescription = it))
                },
                label = "Краткое описание"
            )

            Spacer(modifier = Modifier.height(10.dp))

            DefaultField(
                text = invitation.description,
                onValueChange = {
                    viewModel.updateInvitation(invitation.copy(description = it))
                },
                label = "Описание",
                modifier = Modifier.height(150.dp)
            )

            Spacer(modifier = Modifier.height(10.dp))

            var requiredMemberText by remember { mutableStateOf(invitation.requiredMember.toString()) }

            DefaultField(
                text = requiredMemberText,
                onValueChange = { newText ->
                    // Разрешаем пустое и числовое
                    if (newText.isEmpty() || newText.toIntOrNull() != null) {
                        requiredMemberText = newText
                        val value = newText.toIntOrNull() ?: 0
                        viewModel.updateInvitation(invitation.copy(requiredMember = value))
                    }
                },
                label = "Необходимое количество участников",
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            Spacer(modifier = Modifier.height(10.dp))

            DatePickerField(
                label = "Дата",
                selectedDate = invitation.deadLine.toLocalDate(),
                onDateSelected = {
                    viewModel.updateInvitation(
                        invitation.copy(
                            deadLine = LocalDateTime.of(it, invitation.deadLine.toLocalTime())
                        )
                    )
                }
            )

            Spacer(modifier = Modifier.padding(10.dp))

            CustomTimePickerField(
                label = "Время",
                time = invitation.deadLine.toLocalTime(),
                onTimeSelected = {
                    viewModel.updateInvitation(
                        invitation.copy(
                            deadLine = LocalDateTime.of(invitation.deadLine.toLocalDate(), it)
                        )
                    )
                }
            )

            Spacer(modifier = Modifier.height(20.dp))

            StateButton(
                actualState = invitationState,
                textButton = textAction,
                onClick = { viewModel.executeAction() },
                buttonWidth = 350.dp,
                columnModifier = Modifier.align(Alignment.Center),
                buttonModifier = Modifier.align(Alignment.Center),
                circularProgressModifier = Modifier.align(Alignment.Center),
                textModifier = Modifier.align(Alignment.Center).padding(start = 55.dp),
            )
        }
      }
    }
}