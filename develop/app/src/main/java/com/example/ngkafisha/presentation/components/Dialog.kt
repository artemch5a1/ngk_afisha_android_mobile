package com.example.ngkafisha.presentation.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

@Composable
fun Dialog(
    showConfirmationDialog: Boolean,
    onClick: () -> Unit,
    onDismissRequest: (() -> Unit),
    onClickNo: (() -> Unit)? = null,
    title: String,
    desc: String,
    okText: String = "Да",
    noOkText: String = "Нет",
) {
    if (showConfirmationDialog) {
        AlertDialog(
            onDismissRequest = { onDismissRequest.invoke() },
            title = {
                Text(text = title)
            },
            text = {
                Text(text = desc)
            },
            confirmButton = {
                TextButton(
                    onClick = onClick
                ) {
                    Text(okText)
                }
            },
            dismissButton = {
                if (onClickNo != null) {
                    TextButton(
                        onClick = onClickNo
                    ) {
                        Text(noOkText)
                    }
                }
            }
        )
    }
}