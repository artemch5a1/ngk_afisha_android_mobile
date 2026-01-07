package com.example.ngkafisha.data.identityService.accountContext.mapper

import com.example.domain.common.enums.Role
import com.example.domain.identityService.accountContext.models.Account
import com.example.domain.identityService.accountContext.models.AccountSession
import com.example.domain.identityService.userContext.models.Student
import com.example.ngkafisha.data.identityService.accountContext.dto.AccountDto
import com.example.ngkafisha.data.identityService.accountContext.dto.LoginResponseDto
import com.example.ngkafisha.data.identityService.accountContext.dto.RegistryStudentDto
import java.time.LocalDate
import java.time.format.DateTimeFormatter

object  AccountMapper {
    fun toAccountSession(loginResponseDto: LoginResponseDto): AccountSession
    {
        val account: Account = Account(
            loginResponseDto.accountId,
            loginResponseDto.email,
            Role.fromInt(loginResponseDto.role));

        return AccountSession(account, loginResponseDto.accessToken);
    }

    fun toDomain(accountDto: AccountDto) : Account
    {
        return Account(
            accountId = accountDto.accountId,
            email = accountDto.email,
            accountRole = Role.fromInt(accountDto.accountRole)
        );
    }

    fun toStudentRegistryDto(email: String, password: String, student: Student)
    : RegistryStudentDto
    {

        val localDate: LocalDate = student.user.birthDate

        val dateString: String = localDate.format(DateTimeFormatter.ISO_DATE)

        return RegistryStudentDto(
            email = email,
            password = password,
            surname = student.user.surname,
            name = student.user.name,
            patronymic = student.user.patronymic,
            dateBirth = dateString,
            groupId = student.groupId
        )
    }
}