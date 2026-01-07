package com.example.ngkafisha.application.identityService.userContext.useCases.postUseCases

import com.example.ngkafisha.application.common.base.BaseUseCase
import com.example.ngkafisha.domain.common.models.CustomResult
import com.example.ngkafisha.domain.identityService.accountContext.abstractions.service.auth.SessionStoreService
import com.example.ngkafisha.domain.identityService.userContext.abstractions.repositories.PostRepository
import com.example.ngkafisha.domain.identityService.userContext.models.Post
import javax.inject.Inject

class GetAllPostUseCase @Inject constructor(
    private val postRepository: PostRepository,
    sessionStoreService: SessionStoreService
) : BaseUseCase<Unit, List<Post>>(sessionStoreService) {

    override suspend fun invokeLogic(request: Unit): CustomResult<List<Post>> {
        val response = postRepository.getAllPosts()

        return CustomResult.success(response)
    }


}