package com.example.ngkafisha.presentation.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.ngkafisha.presentation.components.DefaultField
import com.example.ngkafisha.presentation.components.HyperlinkText
import com.example.ngkafisha.presentation.components.PasswordField
import com.example.ngkafisha.presentation.components.StateButton
import com.example.ngkafisha.presentation.viewmodels.SignInViewModel

@Composable
fun SignInScreen(controlNav: NavHostController, errorStateMessage: String? = null, signInViewModel: SignInViewModel = hiltViewModel())
{
    val actualState by signInViewModel.actualState.collectAsState()
    val signInState = signInViewModel.signInState

    LaunchedEffect(Unit)
    {
        if(errorStateMessage != null){

            signInViewModel.setErrorState(errorStateMessage)
        }
    }

    Box(modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center) {
        Column(modifier = Modifier.padding(16.dp))
        {
            Text(
                text = "Войдите в аккаунт",
                fontSize = 26.sp,
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.W800,
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .align(Alignment.CenterHorizontally))

            Spacer(modifier = Modifier.padding(10.dp))

            DefaultField(label = "Введите email",
                text = signInState.email,
                onValueChange = {it -> signInViewModel.updateSign(signInState.copy(email = it))})

            Spacer(modifier = Modifier.padding(10.dp))

            PasswordField(label = "Введите пароль",
                text = signInState.password,
                onValueChange = {it -> signInViewModel.updateSign(signInState.copy(password = it))})

            Spacer(modifier = Modifier.padding(10.dp))

            HyperlinkText(
                text = "Зарегистрироваться",
                onClick = { controlNav.navigate("signUp") {
                    popUpTo("signIn") { inclusive = true }
                } },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.padding(10.dp))

            StateButton(
                textButton = "Войти",
                actualState = actualState,
                onClick = { signInViewModel.signIn(controlNav) },
                columnModifier = Modifier.align(Alignment.CenterHorizontally),
                buttonModifier = Modifier.align(Alignment.CenterHorizontally),
                circularProgressModifier = Modifier.align(Alignment.CenterHorizontally),
                textModifier = Modifier.align(Alignment.CenterHorizontally),
            )

        }


    }
}