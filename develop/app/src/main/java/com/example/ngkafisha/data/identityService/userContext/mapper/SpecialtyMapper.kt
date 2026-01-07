package com.example.ngkafisha.data.identityService.userContext.mapper

import com.example.domain.identityService.userContext.models.Specialty
import com.example.ngkafisha.data.identityService.userContext.dto.SpecialtyDto

object SpecialtyMapper {

    fun toDomain(specialtyDto: SpecialtyDto) : Specialty {

        return Specialty(
            specialtyId = specialtyDto.specialtyId,
            specialtyTitle = specialtyDto.specialtyTitle
        )

    }

    fun toListDomain(specialtiesDto: List<SpecialtyDto>) : List<Specialty>{

        return specialtiesDto.map { specialtyDto -> toDomain(specialtyDto) }

    }

}