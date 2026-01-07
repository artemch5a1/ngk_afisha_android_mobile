package com.example.data.identityService.userContext.mapper

import com.example.data.identityService.userContext.dto.DepartmentDto
import com.example.domain.identityService.userContext.models.Department
import kotlin.collections.map

object DepartmentMapper {

    fun toDomain(departmentDto: DepartmentDto) : Department {

        return Department(
            departmentId = departmentDto.departmentId,
            title = departmentDto.title
        )

    }

    fun toListDomain(departmentsDto: List<DepartmentDto>) : List<Department>{

        return departmentsDto.map { departmentDto -> toDomain(departmentDto) }

    }

}