package com.example.ngkafisha.domain.identityService.userContext.models

import java.util.UUID

data class Publisher(
    val publisherId: UUID,
    val user: User,
    val postId: Int,
    val post: Post
)
