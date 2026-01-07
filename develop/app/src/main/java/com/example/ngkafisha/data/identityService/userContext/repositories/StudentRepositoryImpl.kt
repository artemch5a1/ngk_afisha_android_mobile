package com.example.ngkafisha.data.identityService.userContext.repositories

import com.example.domain.identityService.userContext.abstractions.repositories.StudentRepository
import com.example.domain.identityService.userContext.models.Student
import com.example.ngkafisha.data.common.apiMethods.safeApiCall
import com.example.ngkafisha.data.identityService.common.remote.IdentityApi
import com.example.ngkafisha.data.identityService.userContext.mapper.StudentMapper
import java.util.UUID
import javax.inject.Inject

class StudentRepositoryImpl @Inject constructor(
    private val identityApi: IdentityApi
) : StudentRepository {
    override suspend fun getStudentById(studentId: UUID): Student {
        return safeApiCall {
            val response = identityApi.getStudentById(studentId)

            StudentMapper.toDomain(response)
        }
    }

    override suspend fun getCurrentStudent(): Student {
        return safeApiCall {
            val response = identityApi.getCurrentStudentAsync()

            StudentMapper.toDomain(response)
        }
    }

    override suspend fun updateStudent(student: Student): UUID {

        return safeApiCall {
            identityApi.updateStudentProfile(StudentMapper.toUpdateStudent(student))
        }

    }


}