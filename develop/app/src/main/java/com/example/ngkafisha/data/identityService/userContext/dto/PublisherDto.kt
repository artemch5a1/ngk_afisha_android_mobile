package com.example.ngkafisha.data.identityService.userContext.dto

import java.util.UUID

data class PublisherDto(
    val publisherId: UUID,
    val user: UserDto,
    val postId: Int,
    val post: PostDto
)
