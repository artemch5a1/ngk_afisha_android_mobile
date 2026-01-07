package com.example.ngkafisha.presentation.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import androidx.navigation.NavHostController
import com.example.ngkafisha.domain.common.enums.Role
import com.example.ngkafisha.presentation.components.Dialog
import com.example.ngkafisha.presentation.components.InvitationImageWithGradientText
import com.example.ngkafisha.presentation.models.states.ActualState
import com.example.ngkafisha.presentation.viewmodels.InvitationScreenViewModel
import java.util.UUID

@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun InvitationScreen(
    nav: NavHostController,
    invitationId: String,
    viewModel: InvitationScreenViewModel = hiltViewModel()
) {
    val actualState by viewModel.actualState.collectAsState()
    val invitation by viewModel.invitation.collectAsState()

    val isAlreadyRequest by viewModel.isAlreadyRequest.collectAsState()

    val deleteInvitationState by viewModel.deleteInvitationState.collectAsState()

    val isAuthor: Boolean = viewModel.sessionInfoStore.currentAccount?.accountRole == Role.Publisher

    var showConfirmationDialog by remember { mutableStateOf(false) }

    LaunchedEffect(invitationId) {
        viewModel.loadInvitation(invitationId)
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
                viewModel.deleteInvitation(
                    invitation?.eventId ?: UUID.randomUUID(),
                    invitation?.invitationId ?: UUID.randomUUID(),
                )
            },
            onDismissRequest = {
                showConfirmationDialog = false
            },
            onClickNo = {
                showConfirmationDialog = false
            },
            title = "Подтверждение",
            desc = "Вы уверены, что хотите удалить это приглашение?"
        )
    }

    if(deleteInvitationState is ActualState.Success){
        nav.navigate("inv") {
            popUpTo("invitationScreen/${invitation?.invitationId}") { inclusive = true }
        }
    }
    else if(deleteInvitationState is ActualState.Error){

        Dialog(
            showConfirmationDialog = true,
            onClick = {
                viewModel.resetDeleteInvitationState()
            },
            onDismissRequest = {
                viewModel.resetDeleteInvitationState()
            },
            title = "Не удалось удалить событие",
            desc = (deleteInvitationState as ActualState.Error).message,
            okText = "Окей"
        )

    }

    LazyColumn(modifier = Modifier.fillMaxSize()) {

        items(1){

        when (actualState) {
            is ActualState.Loading -> {
                Box(Modifier.fillMaxSize(), Alignment.Center) {
                    CircularProgressIndicator()
                }
            }

            is ActualState.Error -> {
                Box(Modifier.fillMaxSize(), Alignment.Center) {
                    Text(
                        text = (actualState as ActualState.Error).message,
                        color = Color.Red
                    )
                }
            }

            is ActualState.Success,
            ActualState.Initialized -> {

                InvitationImageWithGradientText(
                    invitation = invitation,
                    isAuthor = isAuthor,
                    imageUrl = invitation?.event?.downloadUrl,
                    onEvent = { event ->  nav.navigate("eventScreen/${invitation?.event?.eventId}")},
                    onBack = { nav.popBackStack() },
                    onApplyClick = { viewModel.takeRequest() },
                    modifier = Modifier
                        .height(imageHeight),
                    isAlreadyRequest = isAlreadyRequest
                )

                Spacer(Modifier.height(16.dp))

                // ЖАНР + ВОЗРАСТ (пока пустые)
                Row(
                    modifier = Modifier
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .background(Color(0xFFE5E5E5), RoundedCornerShape(16.dp))
                            .padding(12.dp)
                    ) {
                        Column {
                            Text("Жанр", color = Color.Black.copy(alpha = 0.6f))
                            Text("${invitation?.event?.genre?.title}")
                        }
                    }

                    Box(
                        modifier = Modifier
                            .background(Color(0xFFE5E5E5), RoundedCornerShape(16.dp))
                            .padding(12.dp)
                    ) {
                        Column {
                            Text("Возраст", color = Color.Black.copy(alpha = 0.6f))
                            Text("${invitation?.event?.minAge}+")
                        }
                    }

                    Box(
                        modifier = Modifier
                            .background(Color(0xFFE5E5E5), RoundedCornerShape(16.dp))
                            .padding(12.dp)
                    ) {
                        Column {
                            Text("Роль", color = Color.Black.copy(alpha = 0.6f))
                            Text("${invitation?.role?.title}")
                        }
                    }
                }

                Spacer(Modifier.height(24.dp))

                Column(Modifier.padding(horizontal = 16.dp)) {

                    Spacer(Modifier.padding(10.dp))
                    Text(
                        text = invitation?.description ?: "",
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

                    Spacer(Modifier.padding(10.dp))

                    if(isAuthor){
                        Text(
                            text = "Требуется учатников: ${invitation?.requiredMember}",
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontSize = 15.sp,
                                shadow = Shadow(
                                    color = Color.Black.copy(alpha = 0.5f),
                                    offset = Offset(1f, 1f),
                                    blurRadius = 4f
                                )
                            )
                        )

                        Spacer(Modifier.padding(4.dp))

                        Text(
                            text = "Набрано учатников: ${invitation?.acceptedMember}",
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontSize = 15.sp,
                                shadow = Shadow(
                                    color = Color.Black.copy(alpha = 0.5f),
                                    offset = Offset(1f, 1f),
                                    blurRadius = 4f
                                )
                            )
                        )

                        Spacer(Modifier.padding(10.dp))

                        Row() {
                            Button(
                                onClick = {
                                    nav.navigate("updateInvitation/${invitation?.eventId}/${invitation?.invitationId}")
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