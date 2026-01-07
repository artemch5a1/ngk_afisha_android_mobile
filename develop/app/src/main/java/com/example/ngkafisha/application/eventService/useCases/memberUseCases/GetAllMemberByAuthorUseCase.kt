package com.example.ngkafisha.application.eventService.useCases.memberUseCases

import com.example.ngkafisha.application.common.base.BaseUseCase
import com.example.ngkafisha.application.identityService.userContext.useCases.studentUseCases.GetStudentByIdUseCase
import com.example.ngkafisha.domain.common.models.CustomResult
import com.example.ngkafisha.domain.eventService.abstractions.repositories.MemberRepository
import com.example.ngkafisha.domain.eventService.models.Member
import com.example.ngkafisha.domain.identityService.accountContext.abstractions.service.auth.SessionStoreService
import javax.inject.Inject

class GetAllMemberByAuthorUseCase @Inject constructor(
    private val memberRepository: MemberRepository,
    private val getStudentByIdUseCase: GetStudentByIdUseCase,
    sessionStoreService: SessionStoreService
) : BaseUseCase<GetAllMemberByAuthorUseCase.Request, List<Member>>(sessionStoreService) {

    override suspend fun invokeLogic(
        request: Request
    ): CustomResult<List<Member>> {

        val members = memberRepository.getAllMemberByAuthor(
            request.skip,
            request.take
        )

        members.forEach { member ->
            val studentResult = getStudentByIdUseCase(
                GetStudentByIdUseCase.Request(member.studentId)
            )

            if (studentResult.isSuccess) {
                member.student = studentResult.value
            }
        }

        return CustomResult.success(members)
    }

    data class Request(
        val skip: Int = 0,
        val take: Int = Int.MAX_VALUE
    )
}