package com.example.ngkafisha.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.ngkafisha.presentation.components.EventSearchField
import com.example.ngkafisha.presentation.components.InvitationCard
import com.example.ngkafisha.presentation.components.myFieldSearch
import com.example.ngkafisha.presentation.models.states.ActualState
import com.example.ngkafisha.presentation.viewmodels.ListInvitationScreenViewModel

@Composable
fun ListInvitationScreen(
    controlNav: NavHostController,
    eventId: String? = null,
    viewModel: ListInvitationScreenViewModel = hiltViewModel()
) {
    val textSearch by viewModel.textSearch.observeAsState("")
    val eventTextSearch by viewModel.eventSearchText.observeAsState("")
    val invitations by viewModel.invitations.observeAsState(emptyList())
    val actualState by viewModel.actualState.collectAsState()
    val isEventMode by viewModel.isEventMode.observeAsState(false)

    val changed by viewModel.changed.observeAsState(false)


    LaunchedEffect(Unit) {

        if(eventId == null){
            viewModel.refresh()
        }
        else{
            viewModel.loadByEvent(eventId)
        }

    }

    if(changed && eventId != null){
        controlNav.navigate("inv")
    }

    Column(modifier = Modifier.fillMaxSize()) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background)
                .padding(top = 40.dp, bottom = 8.dp)
        ) {
            Column() {
                myFieldSearch(
                    myText = "Найти",
                    text = textSearch,
                    onValueChange = {
                        viewModel.updateTextSearch(it)
                    },
                    OnClick = {
                        viewModel.refresh()
                    }
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Заголовок и кнопка в одну строку
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth().padding(start = 20.dp)
                ) {
                    Text(
                        text = "Приглашения",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        textDecoration = TextDecoration.Underline
                    )

                    Spacer(modifier = Modifier.width(20.dp))
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Новый поиск по событию
                EventSearchField(
                    text = eventTextSearch,
                    label = "Событие",
                    enabled = !isEventMode,
                    onValueChange = { viewModel.updateEventSearch(it)
                        },
                    Modifier
                        .padding(end = 25.dp)
                        .padding(start = 20.dp)
                        .height(50.dp)
                )


            }
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
        ) {
            when (actualState) {
                is ActualState.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                is ActualState.Error -> {
                    Text(
                        text = (actualState as ActualState.Error).message,
                        modifier = Modifier.padding(16.dp)
                    )
                }

                is ActualState.Initialized,
                is ActualState.Success -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(invitations.size) { index ->
                            InvitationCard(
                                invitation = invitations[index],
                                onMoreClick = {
                                    controlNav.navigate("invitationScreen/${invitations[index].invitationId}")
                                },
                                onEventClick = { event ->
                                    controlNav.navigate("eventScreen/${event.eventId}")
                                }
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                        }
                    }
                }
            }
        }
    }
}