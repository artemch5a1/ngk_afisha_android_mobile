package com.example.ngkafisha.data.identityService.userContext.repositories

import com.example.domain.identityService.userContext.abstractions.repositories.PostRepository
import com.example.domain.identityService.userContext.models.Post
import com.example.ngkafisha.data.common.apiMethods.safeApiCall
import com.example.ngkafisha.data.identityService.common.remote.IdentityApi
import com.example.ngkafisha.data.identityService.userContext.mapper.PostMapper
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
