package com.example.ngkafisha.domain.identityService.userContext.models

data class Group(
    val groupId:Int,
    val course:Int,
    val numberGroup:Int,
    val specialtyId:Int,
    val specialty: Specialty,
    val fullName: String
)
