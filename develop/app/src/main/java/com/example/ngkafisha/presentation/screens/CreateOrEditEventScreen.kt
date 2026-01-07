package com.example.ngkafisha.presentation.screens

import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
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
import com.example.ngkafisha.presentation.viewmodels.CreateOrEditEventViewModel
import java.io.File
import java.time.LocalDateTime

@Composable
fun CreateOrEditEventScreen(
    controlNav: NavController,
    eventId:String?,
    viewModel: CreateOrEditEventViewModel = hiltViewModel()
){

    LaunchedEffect(eventId) {
        viewModel.loadEvent(eventId)
    }

    val state by viewModel.actualState.collectAsState()
    val eventState by viewModel.eventState.collectAsState()
    val event = viewModel.eventCard
    val genres by viewModel.genres.observeAsState(emptyList())
    val types by viewModel.types.observeAsState(emptyList())
    val locations by viewModel.locations.observeAsState(emptyList())
    val textAction by viewModel.textAction.observeAsState("Создать событие")

    val textPage by viewModel.textPage.observeAsState("Создать событие")

    var selectedImageFile by remember { mutableStateOf<File?>(null) }
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }

    val context = LocalContext.current

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        if (uri != null) {
            selectedImageUri = uri
            selectedImageFile = uri.toFile(context)
        }
    }

    if (state is ActualState.Loading || event == null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                modifier = Modifier.size(48.dp),
                color = Color.Blue,
                strokeWidth = 4.dp
            )
        }
        return
    }

    if(eventState is ActualState.Success){
        controlNav.navigate("listEventScreen") {
            popUpTo("createEventScreen") { inclusive = true }
        }
    }

    Box(modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center) {
        LazyColumn(
            modifier = Modifier
                .padding(16.dp)
                .align(alignment = Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally) {
            items(1){

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

                Spacer(modifier = Modifier.padding(15.dp))

                DefaultField(
                    text = event.title,
                    onValueChange = {
                        viewModel.updateEvent(event.copy(title = it))
                    },
                    label = "Название события"
                )

                Spacer(modifier = Modifier.padding(10.dp))

                DefaultField(
                    text = event.shortDescription,
                    onValueChange = {
                        viewModel.updateEvent(event.copy(shortDescription = it))
                    },
                    label = "Краткое описание",
                    modifier = Modifier
                        .padding(horizontal = 14.dp)
                        .height(100.dp)
                )

                Spacer(modifier = Modifier.padding(10.dp))

                DefaultField(
                    text = event.description,
                    onValueChange = {
                        viewModel.updateEvent(event.copy(description = it))
                    },
                    label = "Описание",
                    modifier = Modifier
                        .padding(horizontal = 14.dp)
                        .height(150.dp)
                )

                Spacer(modifier = Modifier.padding(10.dp))

                DropdownField(
                    label = "Тип",
                    items = types,
                    displayMember = { it?.title ?: "" },
                    selectedItem = types.firstOrNull { it.typeId == event.typeId },
                    onItemSelected = {
                        viewModel.updateEvent(event.copy(typeId = it.typeId))
                    }
                )

                Spacer(modifier = Modifier.padding(10.dp))

                DropdownField(
                    label = "Жанр",
                    items = genres,
                    displayMember = { it?.title ?: "" },
                    selectedItem = genres.firstOrNull { it.genreId == event.genreId },
                    onItemSelected = {
                        viewModel.updateEvent(event.copy(genreId = it.genreId))
                    }
                )

                Spacer(modifier = Modifier.padding(10.dp))

                DropdownField(
                    label = "Локация",
                    items = locations,
                    displayMember = { it?.title ?: "" },
                    selectedItem = locations.firstOrNull { it.locationId == event.locationId },
                    onItemSelected = {
                        viewModel.updateEvent(event.copy(locationId = it.locationId))
                    },
                    textModifier = Modifier
                        .padding(horizontal = 14.dp)
                        .height(80.dp)
                )

                Spacer(modifier = Modifier.padding(10.dp))

                DropdownField(
                    label = "Выберите возрастное исключение",
                    items = listOf(14, 16, 18),
                    selectedItem = event.minAge,
                    displayMember = { "${it}+" },
                    onItemSelected = {
                        viewModel.updateEvent(event.copy(minAge = it))}
                )

                Spacer(modifier = Modifier.padding(10.dp))

                DatePickerField(
                    label = "Дата",
                    selectedDate = event.dateStart.toLocalDate(),
                    onDateSelected = {
                        viewModel.updateEvent(event.copy(
                            dateStart = LocalDateTime.of(it, event.dateStart.toLocalTime())
                        ))
                    }
                )

                Spacer(modifier = Modifier.padding(10.dp))

                CustomTimePickerField(
                    label = "Время",
                    time = event.dateStart.toLocalTime(),
                    onTimeSelected = {
                        viewModel.updateEvent(event.copy(
                            dateStart = LocalDateTime.of(event.dateStart.toLocalDate(), it)
                        ))
                    }
                )

                Spacer(modifier = Modifier.padding(10.dp))

                Text("Фото:", fontSize = 16.sp)

                Spacer(modifier = Modifier.padding(10.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color.LightGray)
                        .clickable { imagePickerLauncher.launch("image/*") },
                    contentAlignment = Alignment.Center
                ) {
                    if (selectedImageUri != null) {
                        AsyncImage(
                            model = selectedImageUri,
                            contentDescription = "",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    } else if(event.downloadUrl.isNotBlank()) {
                        AsyncImage(
                            model = event.downloadUrl,
                            contentDescription = "",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    }
                    else {
                        Text("Нажмите для выбора изображения")
                    }
                }

                Spacer(modifier = Modifier.padding(10.dp))

                StateButton(
                    actualState = eventState,
                    textButton = textAction,
                    onClick = {
                        viewModel.executeAction(selectedImageFile)
                    },
                    columnModifier = Modifier.align(Alignment.Center),
                    buttonModifier = Modifier.align(Alignment.Center),
                    circularProgressModifier = Modifier.align(Alignment.Center),
                    textModifier = Modifier.align(Alignment.Center),
                    buttonWidth = 250.dp
                )

                Spacer(modifier = Modifier.padding(15.dp))
            }
        }
    }
}

fun Uri.toFile(context: Context): File {
    val input = context.contentResolver.openInputStream(this)!!
    val file = File(context.cacheDir, "event_${System.currentTimeMillis()}.jpg")
    file.outputStream().use { output -> input.copyTo(output) }
    return file
}