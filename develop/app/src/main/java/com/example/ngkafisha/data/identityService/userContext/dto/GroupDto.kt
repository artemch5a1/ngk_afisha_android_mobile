package com.example.ngkafisha.data.identityService.userContext.dto

import kotlinx.serialization.Serializable

@Serializable
data class GroupDto(
    val groupId:Int,
    val course:Int,
    val numberGroup:Int,
    val specialtyId:Int,
    val specialty: SpecialtyDto,
    val fullName: String
)
