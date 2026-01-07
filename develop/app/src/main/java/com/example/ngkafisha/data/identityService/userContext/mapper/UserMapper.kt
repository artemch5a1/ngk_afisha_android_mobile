package com.example.ngkafisha.data.identityService.userContext.mapper

import com.example.ngkafisha.data.identityService.userContext.dto.UpdateUserDto
import com.example.ngkafisha.data.identityService.userContext.dto.UserDto
import com.example.ngkafisha.domain.identityService.userContext.models.User
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

object UserMapper {

    fun toDomain(userDto: UserDto) : User
    {
        return User(
            userId = userDto.userId,
            surname = userDto.surname,
            name = userDto.name,
            patronymic = userDto.patronymic,
            birthDate = LocalDate.parse(userDto.birthDate)
        )
    }

    fun toUpdateUserInfo(user: User) : UpdateUserDto{

        val localDate: LocalDate = user.birthDate

        val dateString: String = localDate.format(DateTimeFormatter.ISO_DATE)

        return UpdateUserDto(
            surname = user.surname,
            name = user.name,
            patronymic = user.patronymic,
            dateBirth = dateString
        )

    }
}