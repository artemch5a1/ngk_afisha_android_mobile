package com.example.ngkafisha.data.identityService.userContext.mapper

import com.example.domain.identityService.userContext.models.Group
import com.example.ngkafisha.data.identityService.userContext.dto.GroupDto

object GroupMapper {

    fun toDomain(dto: GroupDto) : Group
    {
        return Group(
            dto.groupId,
            dto.course,
            dto.numberGroup,
            dto.specialtyId,
            SpecialtyMapper.toDomain(dto.specialty),
            dto.fullName
        );
    }

    fun toListDomain(dto: List<GroupDto>): List<Group>{
        return dto.map { dto -> toDomain(dto) }
    }
}