package com.example.domain.identityService.userContext.abstractions.repositories


import com.example.domain.identityService.userContext.models.Student
import java.util.UUID

interface StudentRepository {

    suspend fun getStudentById(studentId: UUID) : Student

    suspend fun getCurrentStudent() : Student

    suspend fun updateStudent(student: Student) : UUID
}