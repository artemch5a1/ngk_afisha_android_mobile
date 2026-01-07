package com.example.ngkafisha.application.eventService.useCases.memberUseCases

import com.example.domain.common.models.CustomResult
import com.example.domain.eventService.abstractions.repositories.MemberRepository
import com.example.domain.eventService.models.Member
import com.example.domain.identityService.accountContext.abstractions.service.auth.SessionStoreService
import com.example.ngkafisha.application.common.base.BaseUseCase
import javax.inject.Inject

class GetAllMemberByStudentUseCase @Inject constructor(
    private val memberRepository: MemberRepository,
    sessionStoreService: SessionStoreService
) : BaseUseCase<GetAllMemberByStudentUseCase.Request, List<Member>>(sessionStoreService) {



    override suspend fun invokeLogic(request: Request): CustomResult<List<Member>> {
        val response = memberRepository.getAllMemberByStudent(request.skip, request.take)

        return CustomResult.success(response)
    }

    data class Request(val skip: Int = 0, val take: Int = Int.MAX_VALUE)

}