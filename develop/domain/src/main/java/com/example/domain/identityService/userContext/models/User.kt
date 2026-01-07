package com.example.domain.identityService.userContext.models

import java.time.LocalDate
import java.util.UUID

data class User(
    val userId: UUID = UUID.randomUUID(),
    val surname: String,
    val name: String,
    val patronymic: String,
    val birthDate: LocalDate
){
    fun fullName(): String{

        return "$surname $name $patronymic"

    }
}
