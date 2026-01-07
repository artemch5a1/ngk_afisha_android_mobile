package com.example.ngkafisha.presentation.models.states

import java.time.LocalDate

data class StudentSignUpState(
    val email: String = "",
    val password: String = "",
    val repeatPassword: String = "",
    val confirmPass: String = "",
    val name:String = "",
    val surname: String = "",
    val patronymic: String = "",
    val dateBirth: LocalDate = LocalDate.now(),
    val groupId:Int = -1
)
