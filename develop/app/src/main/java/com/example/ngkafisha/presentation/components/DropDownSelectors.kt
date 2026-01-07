package com.example.ngkafisha.presentation.components

import android.app.DatePickerDialog
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import com.example.ngkafisha.domain.identityService.userContext.models.Group
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar

private val FieldShape = RoundedCornerShape(40.dp)
private val LabelTextStyle = TextStyle(
    color = Color("#E4E0E0".toColorInt()),
    fontWeight = FontWeight.Bold,
    fontSize = 16.sp,
    background = Color.Transparent,
)

@Composable
fun GroupDropdownField(
    label: String,
    groups: List<Group>,
    selectedGroup: Group?,
    onGroupSelected: (Group) -> Unit,
    modifier: Modifier = Modifier,
    textModifier: Modifier = Modifier,
) {
    var expanded by remember { mutableStateOf(false) }
    val displayText = selectedGroup?.fullName ?: ""

    Box(modifier = modifier) {
        OutlinedTextField(
            value = displayText,
            onValueChange = {},  // Запрещаем ручное редактирование
            readOnly = true,     // Только выбор из списка
            placeholder = { Text(label, style = LabelTextStyle) },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color("#A9A9A9".toColorInt()),
                unfocusedContainerColor = Color("#A9A9A9".toColorInt()),
                unfocusedIndicatorColor = Color("#A9A9A9".toColorInt()),
                focusedIndicatorColor = Color("#A9A9A9".toColorInt()),
                disabledContainerColor = Color("#A9A9A9".toColorInt()),
                disabledIndicatorColor = Color("#A9A9A9".toColorInt()),
                disabledSupportingTextColor = Color.Black,
                disabledTextColor = Color.Black,
                errorSupportingTextColor = Color.Black,
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black
            ),
            shape = FieldShape,
            enabled = false,
            modifier = textModifier
                .width(310.dp).height(50.dp)
                .clickable {
                    expanded = true
                           },
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.fillMaxWidth().height(200.dp)
        ) {
            LazyColumn(modifier = Modifier.width(100.dp).height(200.dp)) {
                item {
                    groups.forEach { group ->
                        DropdownMenuItem(
                            text = { Text(group.fullName, style = LabelTextStyle, color = Color.Black) },
                            onClick = {
                                onGroupSelected(group)
                                expanded = false
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun <T> DropdownField(
    label: String,
    items: List<T>,
    displayMember: (T?) -> String,
    selectedItem: T?,
    onItemSelected: (T) -> Unit,
    modifier: Modifier = Modifier,
    textModifier: Modifier = Modifier,
) {
    var expanded by remember { mutableStateOf(false) }
    val displayText = displayMember(selectedItem)

    Box(modifier = modifier) {
        OutlinedTextField(
            value = displayText,
            onValueChange = {},
            readOnly = true,
            singleLine = false,
            maxLines = 2,
            placeholder = { Text(label, style = LabelTextStyle, overflow = TextOverflow.Ellipsis) },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color("#A9A9A9".toColorInt()),
                unfocusedContainerColor = Color("#A9A9A9".toColorInt()),
                unfocusedIndicatorColor = Color("#A9A9A9".toColorInt()),
                focusedIndicatorColor = Color("#A9A9A9".toColorInt()),
                disabledContainerColor = Color("#A9A9A9".toColorInt()),
                disabledIndicatorColor = Color("#A9A9A9".toColorInt()),
                disabledSupportingTextColor = Color.Black,
                disabledTextColor = Color.Black,
                errorSupportingTextColor = Color.Black,
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black
            ),
            shape = FieldShape,
            enabled = false,
            modifier = textModifier.width(310.dp)

                .clickable {
                    expanded = true
                },
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.fillMaxWidth().height(200.dp)
        ) {
            LazyColumn(modifier = Modifier.width(500.dp).height(200.dp)) {
                item {
                    items.forEach { group ->
                        DropdownMenuItem(
                            text = { Text(displayMember(group), style = LabelTextStyle, color = Color.Black, modifier = Modifier.fillMaxWidth()) },
                            onClick = {
                                onItemSelected(group)
                                expanded = false
                            },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
        }
    }
}


@Composable
private fun fieldColors() = TextFieldDefaults.colors(
    focusedContainerColor = Color("#A9A9A9".toColorInt()),
    unfocusedContainerColor = Color("#A9A9A9".toColorInt()),
    unfocusedIndicatorColor = Color("#A9A9A9".toColorInt()),
    focusedIndicatorColor = Color("#A9A9A9".toColorInt())
)

@Composable
fun DatePickerField(
    label: String,
    selectedDate: LocalDate?,
    onDateSelected: (LocalDate) -> Unit,
    modifier: Modifier = Modifier,
    textModifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    var displayText by remember { mutableStateOf(selectedDate?.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")) ?: "") }

    val calendar = Calendar.getInstance()
    val datePickerDialog = DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            val date = LocalDate.of(year, month + 1, dayOfMonth)
            displayText = date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
            onDateSelected(date)
        },
        selectedDate?.year ?: calendar.get(Calendar.YEAR),
        (selectedDate?.monthValue ?: (calendar.get(Calendar.MONTH) + 1)) - 1,
        selectedDate?.dayOfMonth ?: calendar.get(Calendar.DAY_OF_MONTH)
    )

    Box(modifier = modifier) {
        OutlinedTextField(
            value = displayText,
            onValueChange = {},
            readOnly = true,
            placeholder = { Text(label, style = LabelTextStyle) },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color("#A9A9A9".toColorInt()),
                unfocusedContainerColor = Color("#A9A9A9".toColorInt()),
                unfocusedIndicatorColor = Color("#A9A9A9".toColorInt()),
                focusedIndicatorColor = Color("#A9A9A9".toColorInt()),
                disabledContainerColor = Color("#A9A9A9".toColorInt()),
                disabledIndicatorColor = Color("#A9A9A9".toColorInt()),
                disabledSupportingTextColor = Color.Black,
                disabledTextColor = Color.Black,
                errorSupportingTextColor = Color.Black,
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black
            ),
            shape = FieldShape,
            enabled = false,
            modifier = textModifier
                .width(310.dp).height(50.dp)
                .clickable { datePickerDialog.show() }
        )
    }
}