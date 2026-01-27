package com.example.ngkafisha.presentation.components

import android.app.DatePickerDialog
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.domain.identityService.userContext.models.Group
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar

private val FieldShape = RoundedCornerShape(40.dp)

@Composable
private fun labelTextStyle(): TextStyle = TextStyle(
    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f),
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
            placeholder = { Text(label, style = labelTextStyle()) },
            colors = fieldColors(),
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
                            text = {
                                Text(
                                    group.fullName,
                                    style = labelTextStyle(),
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            },
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
            placeholder = { Text(label, style = labelTextStyle(), overflow = TextOverflow.Ellipsis) },
            colors = fieldColors(),
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
                            text = {
                                Text(
                                    displayMember(group),
                                    style = labelTextStyle(),
                                    color = MaterialTheme.colorScheme.onSurface,
                                    modifier = Modifier.fillMaxWidth()
                                )
                            },
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
    focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
    unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
    disabledContainerColor = MaterialTheme.colorScheme.surfaceVariant,
    focusedIndicatorColor = MaterialTheme.colorScheme.primary,
    unfocusedIndicatorColor = MaterialTheme.colorScheme.outlineVariant,
    disabledIndicatorColor = MaterialTheme.colorScheme.outlineVariant,
    focusedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
    unfocusedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
    disabledTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
    cursorColor = MaterialTheme.colorScheme.primary
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
            placeholder = { Text(label, style = labelTextStyle()) },
            colors = fieldColors(),
            shape = FieldShape,
            enabled = false,
            modifier = textModifier
                .width(310.dp).height(50.dp)
                .clickable { datePickerDialog.show() }
        )
    }
}