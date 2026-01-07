package com.example.ngkafisha.data.identityService.userContext.mapper

import com.example.domain.identityService.userContext.models.Student
import com.example.ngkafisha.data.identityService.userContext.dto.StudentDto
import com.example.ngkafisha.data.identityService.userContext.dto.UpdateStudentProfileDto

object StudentMapper {

    fun toDomain(studentDto: StudentDto) : Student {

        return Student(
            studentId = studentDto.studentId,
            user = UserMapper.toDomain(studentDto.user),
            groupId = studentDto.groupId,
            group = GroupMapper.toDomain(studentDto.group)
        )

    }

    fun toListDomain(studentDtos: List<StudentDto>): List<Student>{

        return studentDtos.map { studentDto -> toDomain(studentDto) }

    }

    fun toUpdateStudent(student: Student) : UpdateStudentProfileDto {

        return UpdateStudentProfileDto(
            student.groupId
        )

    }

}