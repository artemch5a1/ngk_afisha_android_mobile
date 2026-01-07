package com.example.ngkafisha.domain.identityService.userContext.abstractions.repositories

import com.example.ngkafisha.domain.identityService.userContext.models.Post

interface PostRepository {

    suspend fun getAllPosts() : List<Post>

}