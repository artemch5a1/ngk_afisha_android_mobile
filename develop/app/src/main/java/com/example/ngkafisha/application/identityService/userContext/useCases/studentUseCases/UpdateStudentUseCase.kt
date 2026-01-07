package com.example.ngkafisha.application.identityService.userContext.useCases.studentUseCases

import com.example.ngkafisha.application.common.base.BaseUseCase
import com.example.ngkafisha.domain.common.models.CustomResult
import com.example.ngkafisha.domain.identityService.accountContext.abstractions.service.auth.SessionStoreService
import com.example.ngkafisha.domain.identityService.userContext.abstractions.repositories.StudentRepository
import com.example.ngkafisha.domain.identityService.userContext.models.Student
import java.util.UUID
import javax.inject.Inject

class UpdateStudentUseCase @Inject constructor(
    private val studentRepository: StudentRepository,
    sessionStoreService: SessionStoreService
) : BaseUseCase<UpdateStudentUseCase.Request, UUID>(sessionStoreService) {


    override suspend fun invokeLogic(request: Request): CustomResult<UUID> {
        val response = studentRepository.updateStudent(request.student)

        return CustomResult.Companion.success(response)
    }


    data class Request(val student: Student)
}