package com.example.data.identityService.userContext.repositories

import com.example.data.common.apiMethods.safeApiCall
import com.example.data.identityService.common.remote.IdentityApi
import com.example.data.identityService.userContext.mapper.PostMapper
import com.example.domain.identityService.userContext.abstractions.repositories.PostRepository
import com.example.domain.identityService.userContext.models.Post
import javax.inject.Inject

class PostRepositoryImpl @Inject constructor(
    private val identityApi: IdentityApi
) : PostRepository {

    override suspend fun getAllPosts(): List<Post> {
        return safeApiCall {
            val response = identityApi.getAllPostsAsync()

            PostMapper.toDomainList(response)
        }
    }
}
