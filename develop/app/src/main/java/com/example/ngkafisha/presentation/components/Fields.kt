package com.example.ngkafisha.presentation.components

import android.app.TimePickerDialog
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ngkafisha.R
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Locale

private val FieldShape = RoundedCornerShape(40.dp)
private val FieldModifier = Modifier.width(310.dp).height(50.dp)

@Composable
private fun labelTextStyle(): TextStyle = TextStyle(
    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f),
    fontWeight = FontWeight.Bold,
    fontSize = 16.sp,
    background = Color.Transparent,
)

@Composable
fun DefaultField(
    label: String,
    text: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier? = Modifier,
    keyboardOptions: KeyboardOptions? = null
) {
    OutlinedTextField(
        value = text,
        onValueChange = onValueChange,
        placeholder = { Text(label, style = labelTextStyle()) },
        shape = FieldShape,
        modifier = modifier?.width(310.dp) ?:
        FieldModifier,
        colors = fieldColors(),
        keyboardOptions = keyboardOptions ?: KeyboardOptions.Default
    )
}

@Composable
fun EventSearchField(
    text: String,
    label: String,
    enabled: Boolean = false,
    onValueChange: (String) -> Unit,
    modifier: Modifier? = null
) {
    OutlinedTextField(
        value = text,
        onValueChange = onValueChange,
        placeholder = {
            Text(
                label,
                fontSize = 15.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontWeight = FontWeight.Bold
            )
        },
        shape = RoundedCornerShape(15.dp),
        modifier = modifier?.width(310.dp) ?:
        FieldModifier,
        enabled = enabled,
        colors = fieldColors()
    )
}

@Composable
fun PasswordField(
    label: String,
    text: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier? = null
) {
    var passwordVisible by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = text,
        onValueChange = onValueChange,
        placeholder = { Text(label, style = labelTextStyle()) },
        shape = FieldShape,
        modifier = modifier?.width(310.dp) ?:
        FieldModifier,
        colors = fieldColors(),
        visualTransformation = if (passwordVisible)
            VisualTransformation.None
        else
            PasswordVisualTransformation(),
        trailingIcon = {
            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                val iconRes = if (passwordVisible) R.drawable.eye_open else R.drawable.eye_close
                val description = if (passwordVisible) "Hide password" else "Show password"
                Icon(painter = painterResource(iconRes), contentDescription = description)
            }
        }
    )
}

@Composable
fun HyperlinkText(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        fontSize = 16.sp,
        color = MaterialTheme.colorScheme.primary,
        fontWeight = FontWeight.W400,
        textDecoration = TextDecoration.Underline,
        modifier = modifier.clickable { onClick() }
    )
}

@Composable
fun LimitedText(
    message: String,
    modifier: Modifier = Modifier,
    color: Color = Color.Green
) {
    Text(
        text = message,
        fontSize = 16.sp,
        fontWeight = FontWeight.W400,
        color = color,
        maxLines = 3, // ограничение по количеству строк
        overflow = TextOverflow.Ellipsis, // если текста больше — показываем ...
        textAlign = TextAlign.Center,
        modifier = modifier
    )
}

@Composable
fun myFieldSearch(myText: String, text: String, onValueChange: (String) -> Unit, OnClick: () -> Unit) {
    var passSee by remember { mutableStateOf(false) }

    OutlinedTextField(
        colors = fieldColors(),
        value = text,
        onValueChange = onValueChange,
        label = { Text(myText) },
        modifier = Modifier.fillMaxWidth(),
        trailingIcon = {
            IconButton(onClick = OnClick) {
                Icon(
                    imageVector = Icons.Default.Refresh,
                    contentDescription = "перезагрузка"
                )
            }
        }
    )
}

@Composable
fun DateTimeText(
    eventDate: LocalDateTime?,
    modifier: Modifier = Modifier,
    fontSize: TextUnit = 15.sp,
    color: Color? = null
) {
    val formatter = DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm", Locale.getDefault())
    Text(
        text = eventDate?.format(formatter) ?: "",
        fontSize = fontSize,
        color = color ?: MaterialTheme.colorScheme.onBackground,
        fontWeight = FontWeight.W800,
        modifier = modifier
            .padding(bottom = 16.dp)
    )
}

@Composable
fun CustomTimePickerField(
    label: String,
    time: LocalTime?,
    onTimeSelected: (LocalTime) -> Unit
) {
    val context = LocalContext.current
    val displayText = time?.format(DateTimeFormatter.ofPattern("HH:mm")) ?: ""

    val dialog = TimePickerDialog(
        context,
        { _, hourOfDay, minute ->
            onTimeSelected(LocalTime.of(hourOfDay, minute))
        },
        time?.hour ?: 12,
        time?.minute ?: 0,
        true
    )

    Box(modifier = Modifier
        .then(FieldModifier) // подгоняем под общий размер
        .clickable { dialog.show() }
    ) {
        OutlinedTextField(
            value = displayText,
            onValueChange = {},
            placeholder = { Text(label, style = labelTextStyle()) },
            readOnly = true,
            enabled = false,
            shape = FieldShape,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                unfocusedIndicatorColor = MaterialTheme.colorScheme.outlineVariant,
                focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                disabledContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                disabledIndicatorColor = MaterialTheme.colorScheme.outlineVariant,
                disabledSupportingTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                disabledTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                errorSupportingTextColor = MaterialTheme.colorScheme.error,
                focusedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                unfocusedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
            ),
            modifier = Modifier.fillMaxWidth()
        )
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
