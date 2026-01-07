package com.example.ngkafisha.application.identityService.userContext.useCases.studentUseCases

import com.example.domain.common.models.CustomResult
import com.example.domain.identityService.accountContext.abstractions.service.auth.SessionStoreService
import com.example.domain.identityService.userContext.abstractions.repositories.StudentRepository
import com.example.domain.identityService.userContext.models.Student
import com.example.ngkafisha.application.common.base.BaseUseCase
import java.util.UUID
import javax.inject.Inject

class GetStudentByIdUseCase @Inject constructor(
    sessionStoreService: SessionStoreService,
    private val studentRepository: StudentRepository
) : BaseUseCase<GetStudentByIdUseCase.Request, Student>(sessionStoreService) {


    override suspend fun invokeLogic(request: Request): CustomResult<Student> {
        val response: Student = studentRepository.getStudentById(request.studentId)

        return CustomResult.success(response)
    }

    data class Request(val studentId: UUID)

}