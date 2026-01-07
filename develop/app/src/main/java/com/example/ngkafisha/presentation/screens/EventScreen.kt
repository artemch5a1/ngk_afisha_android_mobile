package com.example.ngkafisha.presentation.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.domain.common.enums.Role
import com.example.ngkafisha.presentation.components.Dialog
import com.example.ngkafisha.presentation.components.EventImageWithGradientText
import com.example.ngkafisha.presentation.models.states.ActualState
import com.example.ngkafisha.presentation.viewmodels.EventScreenViewModel
import java.util.UUID

@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun EventScreen(
    controlNav: NavController,
    eventId:String,
    eventScreenViewModel: EventScreenViewModel = hiltViewModel()
){
    val actualState by eventScreenViewModel.actualState.collectAsState()

    val deleteEventState by eventScreenViewModel.deleteEventState.collectAsState()

    val eventCard = eventScreenViewModel.event

    var showConfirmationDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit)
    {
        eventScreenViewModel.loadEvent(eventId)
    }

    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp

    val imageHeight = remember(screenHeight, screenWidth) {
        minOf(
            screenHeight * 0.60f
        )
    }

    if (showConfirmationDialog) {
        Dialog(
            showConfirmationDialog = showConfirmationDialog,
            onClick = {
                showConfirmationDialog = false
                eventScreenViewModel.deleteEvent(
                    eventCard?.eventId ?: UUID.randomUUID()
                )
            },
            onDismissRequest = {
                showConfirmationDialog = false
            },
            onClickNo = {
                showConfirmationDialog = false
            },
            title = "Подтверждение",
            desc = "Вы уверены, что хотите удалить это событие?"
        )
    }

    if(deleteEventState is ActualState.Success){
        controlNav.navigate("listEventScreen") {
            popUpTo("eventScreen/${eventCard?.eventId}") { inclusive = true }
        }
    }
    else if(deleteEventState is ActualState.Error){

        Dialog(
            showConfirmationDialog = true,
            onClick = {
                eventScreenViewModel.resetDeleteEventState()
            },
            onDismissRequest = {
                eventScreenViewModel.resetDeleteEventState()
            },
            title = "Не удалось удалить событие",
            desc = (deleteEventState as ActualState.Error).message,
            okText = "Окей"
        )

    }

    when(actualState){
        is ActualState.Error ->{
            Text(
                text = (actualState as ActualState.Error).message,
                color = Color.White,
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 10.sp,
                    shadow = Shadow(
                        color = Color.Black.copy(alpha = 0.5f),
                        offset = Offset(1f, 1f),
                        blurRadius = 4f
                    )
                )
            )
        }
        is ActualState.Loading -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ){
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }

        }
        is ActualState.Success, ActualState.Initialized ->{
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                Box(
                    modifier = Modifier.weight(1f)
                ) {
                    LazyColumn(
                        modifier = Modifier

                    ) {
                        items(1) {
                            EventImageWithGradientText(
                                eventCard,
                                eventCard?.downloadUrl,
                                onBack = {
                                    controlNav.popBackStack()
                                },
                                onButtonClick = {
                                    eventId ->

                                    if(eventId != null)
                                        controlNav.navigate("inv/${eventId}")

                                },
                                imageModifier = Modifier
                                    .fillMaxSize()
                                    .height(imageHeight),
                                )

                            // Блок с жанром и возрастом (под изображением)
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                            ) {
                                // Строка с жанром и возрастом
                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .background(Color(0xFFE5E5E5), RoundedCornerShape(16.dp))
                                            .padding(12.dp)
                                    ) {
                                        Column {
                                            Text("Жанр", color = Color.Black.copy(alpha = 0.6f))
                                            Text("${eventCard?.genre?.title}")
                                        }
                                    }

                                    Box(
                                        modifier = Modifier
                                            .background(Color(0xFFE5E5E5), RoundedCornerShape(16.dp))
                                            .padding(12.dp)
                                    ) {
                                        Column {
                                            Text("Возраст", color = Color.Black.copy(alpha = 0.6f))
                                            Text("${eventCard?.minAge}+")
                                        }
                                    }
                                }


                                eventCard?.location?.let { location ->

                                    Spacer(modifier = Modifier.padding(8.dp))

                                    Row(
                                        verticalAlignment = Alignment.Top,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .background(
                                                color = Color(0xFFE5E5E5),
                                                shape = RoundedCornerShape(16.dp)
                                            )
                                            .padding(horizontal = 12.dp, vertical = 8.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Place,
                                            contentDescription = "Локация",
                                            tint = Color(0xFF007926),
                                            modifier = Modifier.padding(end = 8.dp)
                                        )
                                        Column {
                                            Text(
                                                text = location.title,
                                                style = MaterialTheme.typography.bodyLarge.copy(
                                                    fontWeight = FontWeight.Bold,
                                                    fontSize = 18.sp
                                                ),
                                                color = Color.Black.copy(alpha = 0.6f)
                                            )
                                            Text(
                                                text = location.address,
                                                style = MaterialTheme.typography.bodyMedium.copy(
                                                    fontWeight = FontWeight.Normal,
                                                    fontSize = 14.sp
                                                )
                                            )
                                        }
                                    }
                                }

                                if (eventCard?.description != null) {
                                    Column {
                                        Spacer(Modifier.padding(10.dp))
                                        Text(
                                            text = eventCard.description,
                                            color = Color.Black,
                                            style = MaterialTheme.typography.titleLarge.copy(
                                                fontSize = 15.sp,
                                                shadow = Shadow(
                                                    color = Color.Black.copy(alpha = 0.5f),
                                                    offset = Offset(1f, 1f),
                                                    blurRadius = 4f
                                                )
                                            )
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.padding(10.dp))

                                if(eventScreenViewModel.sessionInfoStore.currentAccount?.accountRole == Role.Publisher){
                                    Column  {
                                        Button(
                                            onClick = {
                                                controlNav.navigate("createInvitation/${eventId}")
                                            },
                                            colors = ButtonDefaults.buttonColors(
                                                containerColor = Color("#007926".toColorInt()),
                                                contentColor = Color.White
                                            ),
                                            modifier = Modifier
                                        ) {

                                            Text(text = "Сделать приглашение", fontSize = 18.sp)
                                        }

                                        Spacer(modifier = Modifier.padding(8.dp))

                                        Button(
                                            onClick = {
                                                controlNav.navigate("updateEventScreen/${eventId}")
                                            },
                                            colors = ButtonDefaults.buttonColors(
                                                containerColor = Color("#007926".toColorInt()),
                                                contentColor = Color.White
                                            ),
                                            modifier = Modifier
                                        ) {

                                            Text(text = "Изменить", fontSize = 18.sp)
                                        }

                                        Spacer(modifier = Modifier.padding(8.dp))

                                        Button(
                                            onClick = {
                                                showConfirmationDialog = true
                                            },
                                            colors = ButtonDefaults.buttonColors(
                                                containerColor = Color.Red,
                                                contentColor = Color.White
                                            ),
                                            modifier = Modifier
                                        ) {

                                            Text(text = "Удалить", fontSize = 18.sp)
                                        }
                                    }
                                }

                            }
                        }
                    }
                }
            }
        }
    }
}