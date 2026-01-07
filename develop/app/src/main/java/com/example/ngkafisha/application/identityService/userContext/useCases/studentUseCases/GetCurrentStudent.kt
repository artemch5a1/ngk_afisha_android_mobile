package com.example.ngkafisha.application.identityService.userContext.useCases.studentUseCases

import com.example.ngkafisha.application.common.base.BaseUseCase
import com.example.ngkafisha.domain.common.models.CustomResult
import com.example.ngkafisha.domain.identityService.accountContext.abstractions.service.auth.SessionStoreService
import com.example.ngkafisha.domain.identityService.userContext.abstractions.repositories.StudentRepository
import com.example.ngkafisha.domain.identityService.userContext.models.Student
import javax.inject.Inject

class GetCurrentStudent @Inject constructor(
    sessionStoreService: SessionStoreService,
    private val studentRepository: StudentRepository
) : BaseUseCase<GetCurrentStudent.Request, Student>(sessionStoreService) {


    override suspend fun invokeLogic(request: Request): CustomResult<Student> {
        val response:Student = studentRepository.getCurrentStudent()

        return CustomResult.success(response)
    }

    data class Request(val unit: Unit = Unit)

}