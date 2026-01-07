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
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import androidx.core.graphics.toColorInt
import com.example.ngkafisha.R
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Locale

private val FieldShape = RoundedCornerShape(40.dp)
private val FieldModifier = Modifier.width(310.dp).height(50.dp)
private val LabelTextStyle = TextStyle(
    color = Color("#E4E0E0".toColorInt()),
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
        placeholder = { Text(label, style = LabelTextStyle) },
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
        placeholder = { Text(label,
            fontSize = 15.sp,
            color = Color("#897979".toColorInt()), fontWeight = FontWeight.Bold) },
        shape = RoundedCornerShape(15.dp),
        modifier = modifier?.width(310.dp) ?:
        FieldModifier,
        enabled = enabled,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color("#D9D9D9".toColorInt()),
            unfocusedContainerColor = Color("#D9D9D9".toColorInt()),
            unfocusedIndicatorColor = Color("#D9D9D9".toColorInt()),
            focusedIndicatorColor = Color("#D9D9D9".toColorInt()),
        )
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
        placeholder = { Text(label, style = LabelTextStyle) },
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
        color = Color(0xFF1A73E8),
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
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            unfocusedIndicatorColor = Color.White,
            focusedIndicatorColor = Color.White,
        ),
        value = text,
        onValueChange = onValueChange,
        label = { Text(myText) },
        shape = RoundedCornerShape(15.dp),
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
    color: Color = Color.Black) {
    val formatter = DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm", Locale.getDefault())
    Text(
        text = eventDate?.format(formatter) ?: "",
        fontSize = fontSize,
        color = color,
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
            placeholder = { Text(label, style = LabelTextStyle) },
            readOnly = true,
            enabled = false,
            shape = FieldShape,
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
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun fieldColors() = TextFieldDefaults.colors(
    focusedContainerColor = Color("#A9A9A9".toColorInt()),
    unfocusedContainerColor = Color("#A9A9A9".toColorInt()),
    unfocusedIndicatorColor = Color("#A9A9A9".toColorInt()),
    focusedIndicatorColor = Color("#A9A9A9".toColorInt())
)
