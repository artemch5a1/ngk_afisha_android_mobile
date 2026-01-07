package com.example.ngkafisha.presentation.models.states

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.ngkafisha.presentation.models.interfaces.IHaveTitle

class SelectedState<T : IHaveTitle>(val item: T, isSelected: Boolean = false) {
    var isSelected by mutableStateOf(isSelected)
}