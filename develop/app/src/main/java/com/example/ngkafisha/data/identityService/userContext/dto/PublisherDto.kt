package com.example.ngkafisha.data.identityService.userContext.dto

import com.example.ngkafisha.domain.identityService.userContext.models.User
import java.util.UUID

data class PublisherDto(
    val publisherId: UUID,
    val user: UserDto,
    val postId: Int,
    val post: PostDto
)
