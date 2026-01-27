package com.example.ngkafisha.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.domain.common.enums.Role
import com.example.ngkafisha.presentation.components.EventCard
import com.example.ngkafisha.presentation.components.SelectableButtonList
import com.example.ngkafisha.presentation.components.myFieldSearch
import com.example.ngkafisha.presentation.models.states.ActualState
import com.example.ngkafisha.presentation.viewmodels.ListEventScreenViewModel

@Composable
fun ListEventScreen(
    controlNav: NavHostController,
    listEventScreenViewModel: ListEventScreenViewModel = hiltViewModel()
){

    val textSearch = listEventScreenViewModel.textSearch.observeAsState()

    val actualState by listEventScreenViewModel.actualState.collectAsState()

    val events = listEventScreenViewModel.events.observeAsState(emptyList())

    val types = listEventScreenViewModel.types.observeAsState(emptyList())



    Column(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background)
                .zIndex(1f)
        ) {
            Column {
                Row(
                    modifier = Modifier.padding(top = 40.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    myFieldSearch(
                        myText = "Найти",
                        text = textSearch.value ?: "",
                        onValueChange = {
                            listEventScreenViewModel.updateTextSearch(it)
                            listEventScreenViewModel
                                .filterEvents()
                        },
                        OnClick = {
                            listEventScreenViewModel.refresh()
                        }
                    )
                }

                Spacer(modifier = Modifier.height(15.dp))

                if(listEventScreenViewModel.sessionInfoStore.currentAccount?.accountRole == Role.Publisher){
                    Row {

                        Spacer(modifier = Modifier.width(15.dp))

                        Text(
                            text = "Мои события",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            textDecoration = TextDecoration.Underline,
                            modifier = Modifier.padding(top = 8.dp)
                        )

                        Spacer(modifier = Modifier.width(55.dp))

                        Button(
                            onClick = {
                                controlNav.navigate("createEventScreen")
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                                contentColor = MaterialTheme.colorScheme.onTertiaryContainer
                            ),
                            modifier = Modifier.padding(bottom = 8.dp)
                        ) {
                            Text("+ Добавить")
                        }
                    }
                }

                SelectableButtonList(types.value, onItemSelected = {
                    listEventScreenViewModel.filterEvents()
                })
            }
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
        ) {
            when(actualState) {
                is ActualState.Initialized, is ActualState.Success -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(events.value.size) { index ->
                            EventCard(event = events.value[index].copy(), controlNav, isAuthor = false, isAdmin = false)
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }
                }
                is ActualState.Error -> {
                    Text(
                        text = (actualState as ActualState.Error).message,
                        modifier = Modifier.padding(16.dp)
                    )
                }
                is ActualState.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }
    }

}