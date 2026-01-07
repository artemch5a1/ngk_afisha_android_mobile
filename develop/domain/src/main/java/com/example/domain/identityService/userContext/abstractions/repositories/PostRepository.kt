package com.example.domain.identityService.userContext.abstractions.repositories

import com.example.domain.identityService.userContext.models.Post


interface PostRepository {

    suspend fun getAllPosts() : List<Post>

}