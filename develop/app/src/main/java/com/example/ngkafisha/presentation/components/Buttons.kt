package com.example.ngkafisha.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.domain.eventService.models.EventType
import com.example.ngkafisha.presentation.models.interfaces.IHaveTitle
import com.example.ngkafisha.presentation.models.states.ActualState
import com.example.ngkafisha.presentation.models.states.SelectedState

@Composable
fun DefaultButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    containerColor: Color? = null,
    contentColor: Color? = null,
    fontSize: Int = 18,
    width: Dp = 150.dp
) {
    val resolvedContainerColor = containerColor ?: MaterialTheme.colorScheme.primary
    val resolvedContentColor = contentColor ?: MaterialTheme.colorScheme.onPrimary

    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = resolvedContainerColor,
            contentColor = resolvedContentColor
        ),
        modifier = modifier.height(50.dp).width(width)
    ) {
        Text(text = text, fontSize = fontSize.sp)
    }
}

@Composable
fun StateButton(
    textButton: String,
    actualState: ActualState,
    onClick: () -> Unit,
    columnModifier: Modifier = Modifier,
    buttonModifier: Modifier = Modifier,
    circularProgressModifier: Modifier = Modifier,
    textModifier: Modifier = Modifier,
    buttonWidth: Dp = 150.dp,
    onSuccess:(() -> Unit)? = null
) {
    Column(modifier = columnModifier)
    {
        when (actualState) {
            is ActualState.Initialized -> {
                DefaultButton(
                    text = textButton,
                    onClick = onClick,
                    buttonModifier,
                    width = buttonWidth
                )
            }
            is ActualState.Loading -> {
                CircularProgressIndicator(
                    modifier = circularProgressModifier.size(48.dp),
                    color = MaterialTheme.colorScheme.primary,
                    strokeWidth = 4.dp
                )
            }
            is ActualState.Error -> {
                DefaultButton(
                    text = textButton,
                    onClick = onClick,
                    buttonModifier,
                    width = buttonWidth
                )
                LimitedText(
                    message = actualState.message,
                    color = MaterialTheme.colorScheme.error,
                    modifier = textModifier.padding(top = 8.dp).width(240.dp)
                )
            }
            is ActualState.Success -> {
                if(onSuccess == null){
                    DefaultButton(
                        text = textButton,
                        onClick = onClick,
                        buttonModifier,
                        width = buttonWidth
                    )
                    LimitedText(
                        message = actualState.message,
                        color = MaterialTheme.colorScheme.tertiary,
                        modifier = textModifier.padding(top = 8.dp).width(240.dp)
                    )
                }
                else{
                    onSuccess()
                }
            }
        }
    }
}

@Composable
fun TypeBut(typeEv: EventType?, isSelected: Boolean, OnClick: () -> Unit) {
    var isSelectesNow by remember{ mutableStateOf(isSelected) }
    Button(
        onClick = {
            OnClick()
            isSelectesNow = !isSelectesNow
        },
        modifier = Modifier
            .padding(vertical = 16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelectesNow) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant,
            contentColor = if (isSelectesNow) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant
        )
    ) {
        Text(typeEv?.title ?: "", fontSize = 18.sp)
    }
}


@Composable
fun <T : IHaveTitle> SelectableButton(
    selectedState: SelectedState<T>,
    onClick: () -> Unit
) {
    val isSelectedNow by rememberUpdatedState(selectedState.isSelected)

    Button(
        onClick = {
            selectedState.isSelected = !selectedState.isSelected
            onClick()
        },
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelectedNow) MaterialTheme.colorScheme.surfaceVariant else MaterialTheme.colorScheme.primary,
            contentColor = if (isSelectedNow) MaterialTheme.colorScheme.onSurfaceVariant else MaterialTheme.colorScheme.onPrimary
        ),
        modifier = Modifier.padding(vertical = 8.dp)
    ) {
        Text(selectedState.item.title, fontSize = 18.sp)
    }
}

@Composable
fun <T : IHaveTitle> SelectableButtonList(
    items: List<SelectedState<T>>,
    onItemSelected: (T) -> Unit
) {
    LazyRow(modifier = Modifier.padding(vertical = 8.dp)) {
        items(items) { itemState ->
            SelectableButton(
                selectedState = itemState,
                onClick = {
                    onItemSelected(itemState.item)
                }
            )
            Spacer(modifier = Modifier.width(8.dp))
        }
    }
}
