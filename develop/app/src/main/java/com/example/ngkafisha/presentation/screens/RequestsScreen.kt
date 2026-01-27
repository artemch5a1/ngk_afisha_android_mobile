package com.example.ngkafisha.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.example.domain.common.enums.MemberStatus
import com.example.ngkafisha.presentation.components.InvitationCard
import com.example.ngkafisha.presentation.models.states.ActualState
import com.example.ngkafisha.presentation.viewmodels.RequestsViewModel

@Composable
fun RequestsScreen(
    navController: NavController,
    viewModel: RequestsViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val members by viewModel.members.collectAsState()

    var selectedTabIndex by remember { mutableStateOf(0) }

    LaunchedEffect(Unit) {
        viewModel.loadInvitations()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        // ===== Заголовок =====
        Text(
            text = "Мои заявки",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        // ===== Tabs =====
        TabRow(
            selectedTabIndex = selectedTabIndex
        ) {
            Tab(
                selected = selectedTabIndex == 0,
                onClick = { selectedTabIndex = 0 },
                text = { Text("На рассмотрении") }
            )
            Tab(
                selected = selectedTabIndex == 1,
                onClick = { selectedTabIndex = 1 },
                text = { Text("Приняты") }
            )
        }

        Spacer(Modifier.height(12.dp))

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
                Text(
                    text = (state as ActualState.Error).message,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(16.dp)
                )
            }

            else -> {

                val filteredMembers = remember(members, selectedTabIndex) {
                    when (selectedTabIndex) {
                        0 -> members.filter { it.status == MemberStatus.REVIEW }
                        1 -> members.filter { it.status == MemberStatus.ACCEPTED }
                        else -> emptyList()
                    }
                }

                if (filteredMembers.isEmpty()) {
                    EmptyInvitationsState(selectedTabIndex)
                }else{
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(filteredMembers) { member ->
                            InvitationCard(
                                member = member,
                                onTitleClick = {
                                    navController.navigate(
                                        "invitationScreen/${member.invitation.invitationId}"
                                    )
                                },
                                currentRole = viewModel.sessionInfoStore.currentAccount?.accountRole,
                                onCancelRequest = {member ->
                                    viewModel.cancelRequest(
                                        member.invitation.invitationId,
                                        member.invitation.eventId)
                                },
                                onRejectRequest = {member ->
                                    viewModel.rejectRequest(member.invitation.invitationId,
                                        member.invitation.eventId,
                                        member.studentId)
                                },
                                onAccept = {member ->
                                    viewModel.acceptRequest(
                                        member.invitation.invitationId,
                                        member.invitation.eventId,
                                        member.studentId)
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}


@Composable
private fun EmptyInvitationsState(selectedTabIndex: Int) {
    val text = when (selectedTabIndex) {
        0 -> "Нет заявок на рассмотрении"
        1 -> "Нет принятых заявок"
        else -> ""
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}