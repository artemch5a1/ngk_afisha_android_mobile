package com.example.ngkafisha.data.identityService.userContext.dto

import java.util.UUID

data class StudentDto(
    val studentId: UUID,
    val user: UserDto,
    val groupId: Int,
    val group: GroupDto
)
