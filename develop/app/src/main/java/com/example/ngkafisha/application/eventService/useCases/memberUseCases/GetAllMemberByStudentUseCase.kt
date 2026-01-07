package com.example.ngkafisha.application.eventService.useCases.memberUseCases

import com.example.ngkafisha.application.common.base.BaseUseCase
import com.example.ngkafisha.domain.common.models.CustomResult
import com.example.ngkafisha.domain.eventService.abstractions.repositories.MemberRepository
import com.example.ngkafisha.domain.eventService.models.Member
import com.example.ngkafisha.domain.identityService.accountContext.abstractions.service.auth.SessionStoreService
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