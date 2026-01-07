package com.example.domain.identityService.userContext.models


data class Post(
    val postId: Int,
    val title: String,
    val departmentId: Int,
    val department: Department
)
