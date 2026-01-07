package com.example.ngkafisha.domain.identityService.userContext.models

import java.util.UUID

data class Student(
    val studentId: UUID = UUID.randomUUID(),
    val user: User,
    val groupId:Int,
    val group: Group? = null

)
