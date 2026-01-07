package com.example.ngkafisha.data.identityService.userContext.dto

data class PostDto(
    val postId: Int,
    val title: String,
    val departmentId: Int,
    val department: DepartmentDto
)
