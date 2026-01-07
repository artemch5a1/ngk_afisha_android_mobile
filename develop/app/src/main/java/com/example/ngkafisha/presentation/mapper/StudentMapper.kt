package com.example.ngkafisha.presentation.mapper

import com.example.ngkafisha.domain.identityService.userContext.models.Student
import com.example.ngkafisha.domain.identityService.userContext.models.User
import com.example.ngkafisha.presentation.models.states.StudentSignUpState

object StudentMapper {

    fun toStudent(studentSignUpState: StudentSignUpState) : Student
    {
        val user: User = User(
            surname = studentSignUpState.surname,
            name = studentSignUpState.name,
            patronymic = studentSignUpState.patronymic,
            birthDate = studentSignUpState.dateBirth
        )

        return Student(
            user =  user,
            groupId = studentSignUpState.groupId
        )
    }

}