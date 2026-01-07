package com.example.ngkafisha.presentation.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.domain.identityService.userContext.models.Group
import com.example.ngkafisha.presentation.components.DatePickerField
import com.example.ngkafisha.presentation.components.DefaultField
import com.example.ngkafisha.presentation.components.GroupDropdownField
import com.example.ngkafisha.presentation.components.HyperlinkText
import com.example.ngkafisha.presentation.components.PasswordField
import com.example.ngkafisha.presentation.components.StateButton
import com.example.ngkafisha.presentation.viewmodels.SignUpViewModel

@Composable
fun SignUpScreen(controlNav: NavHostController, signUpViewModel: SignUpViewModel = hiltViewModel())
{
    val actualState by signUpViewModel.actualState.collectAsState()
    val studentSignUpState = signUpViewModel.studentSignUpState

    val groups by signUpViewModel.groups.observeAsState(emptyList())

    Box(modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center) {
        Column(modifier = Modifier.padding(16.dp))
        {
            Text(
                text = "Регистрация",
                fontSize = 26.sp,
                color = Color.Black,
                fontWeight = FontWeight.W800,
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .align(Alignment.CenterHorizontally))

            Spacer(modifier = Modifier.padding(10.dp))

            DefaultField(label = "Введите email",
                text = studentSignUpState.email,
                onValueChange = {it -> signUpViewModel.updateSign(studentSignUpState.copy(email = it))})

            Spacer(modifier = Modifier.padding(10.dp))

            PasswordField(label = "Введите пароль",
                text = studentSignUpState.password,
                onValueChange = {it -> signUpViewModel.updateSign(studentSignUpState.copy(password = it))})

            Spacer(modifier = Modifier.padding(10.dp))

            PasswordField(label = "Повторите пароль",
                text = studentSignUpState.repeatPassword,
                onValueChange = {it -> signUpViewModel.updateSign(studentSignUpState.copy(repeatPassword = it))})

            Spacer(modifier = Modifier.padding(10.dp))

            DefaultField(label = "Введите фамилию",
                text = studentSignUpState.surname,
                onValueChange = {it -> signUpViewModel.updateSign(studentSignUpState.copy(surname = it))})

            Spacer(modifier = Modifier.padding(10.dp))

            DefaultField(label = "Введите имя",
                text = studentSignUpState.name,
                onValueChange = {it -> signUpViewModel.updateSign(studentSignUpState.copy(name = it))})

            Spacer(modifier = Modifier.padding(10.dp))


            DefaultField(label = "Введите отчество",
                text = studentSignUpState.patronymic,
                onValueChange = {it -> signUpViewModel.updateSign(studentSignUpState.copy(patronymic = it))})

            Spacer(modifier = Modifier.padding(10.dp))

            var selectedGroup by remember { mutableStateOf<Group?>(null) }

            GroupDropdownField(
                label = "Выберите группу",
                groups = groups,
                selectedGroup = selectedGroup,
                onGroupSelected = { group ->
                    selectedGroup = group
                    signUpViewModel.updateSign(studentSignUpState.copy(groupId = group.groupId))
                }
            )

            Spacer(modifier = Modifier.padding(10.dp))

            DatePickerField(
                label = "",
                selectedDate = studentSignUpState.dateBirth,
                onDateSelected = { it ->
                    signUpViewModel.updateSign(studentSignUpState.copy(dateBirth = it))
                }
            )

            Spacer(modifier = Modifier.padding(10.dp))

            HyperlinkText(
                text = "Войти",
                onClick = { controlNav.navigate("signIn") {
                    popUpTo("signUp") { inclusive = true }
                } },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.padding(10.dp))

            StateButton(
                textButton = "Зарегистрироваться",
                actualState = actualState,
                onClick = { signUpViewModel.signUp(controlNav) },
                columnModifier = Modifier.align(Alignment.CenterHorizontally),
                buttonModifier = Modifier.align(Alignment.CenterHorizontally),
                circularProgressModifier = Modifier.align(Alignment.CenterHorizontally),
                textModifier = Modifier.align(Alignment.CenterHorizontally),
                buttonWidth = 250.dp
            )

        }


    }
}